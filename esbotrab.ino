#include <LiquidCrystal.h>
#define pinBotoes A0

#define pinRs 8
#define pinEn 9
#define pinD4 4
#define pinD5 5
#define pinD6 6
#define pinD7 7
#define pinBackLight 10


LiquidCrystal lcd(pinRs, pinEn, pinD4, pinD5, pinD6, pinD7);
int c = 0;
void setup() {
  // put your setup code here, to run once:
  lcd.begin(16, 2);
  lcd.clear();

}

void loop() {
  // put your main code here, to run repeatedly:
  int b= analogRead(pinBotoes);
  /*lcd.println(b);
  delay(5000);
  lcd.clear();
  Serial.println(b);*/
  delay(100);
  if(b < 800){
    delay(100);
    c++;
    lcd.clear();
    lcd.println("Digite sua senha:");
    //lcd.setCursor(0,1);
    //lcd.println(c);
    
    
  }

}
