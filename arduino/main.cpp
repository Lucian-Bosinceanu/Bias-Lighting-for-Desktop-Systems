#include <Arduino.h>
#include <FastLED.h>
#include <stdlib.h>

#define DATA_PIN     8
#define NUM_LEDS    82
#define BRIGHTNESS 255  

#define HEADER_SIZE 4

#define LIGHT_COMMAND 0
#define TURN_OFF_COMMAND 2

CRGB leds[NUM_LEDS];

const uint8_t header[HEADER_SIZE] = { 's', 'e', 'n', 'd'};
uint8_t currentByte;
uint8_t currentCommand;

bool isHeader;
uint8_t light_command_buffer[NUM_LEDS * 4];

void solve_command();
void solve_light_command();
void solve_turn_off_command();

void setup() {
    FastLED.addLeds<WS2812, DATA_PIN>(leds, NUM_LEDS);
    FastLED.setBrightness( BRIGHTNESS );
    Serial.begin(115200);
}

void loop() {
    while(true){
        while(Serial.available() == 0){}
        currentByte = Serial.read();
        isHeader = false;

        if (currentByte == header[0]) {
            isHeader = true;        
            for (int i = 1; i < HEADER_SIZE && isHeader; i++) {
                while(Serial.available() == 0){}
                currentByte = Serial.read();
                if (currentByte != header[i]) {
                    isHeader = false;
                }
            }
        }
        
        if (isHeader) {
            while(Serial.available() == 0){}
            currentCommand = Serial.read();
            solve_command();
            break;
        }
    }
    
    FastLED.show();
    while(Serial.available() > 0) { Serial.read(); }
}

void solve_command() {
    if (currentCommand == LIGHT_COMMAND) {
        solve_light_command();
        return;
    }

    if (currentCommand == TURN_OFF_COMMAND) {
        solve_turn_off_command();
        Serial.print('&');
        return;
    }
}

void solve_light_command() {
    uint8_t id, rgbValue1, rgbValue2, rgbValue3;
    int bytesRead = 0;
    int packageSize = 0;

    while(Serial.available() == 0){}
    uint8_t ledCount = Serial.read();
    packageSize = 4 * ledCount;

    while(bytesRead < packageSize){
      bytesRead += Serial.readBytes(light_command_buffer + bytesRead, packageSize - bytesRead);
    }
    
    for (int i = 0; i < 4 * ledCount; i+=4) {
      id = light_command_buffer[i];
      rgbValue1 = light_command_buffer[i+1];
      rgbValue2 = light_command_buffer[i+2];
      rgbValue3 = light_command_buffer[i+3];
      leds[id] = CRGB(rgbValue1, rgbValue2, rgbValue3);    
    }
}

void solve_turn_off_command(){
    for (int i = 0; i < NUM_LEDS; i++)
    {
        leds[i] = 0x000000;
    }
}