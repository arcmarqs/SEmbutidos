//libraries
#include <WiFi.h>
#include <LiquidCrystal.h>
#include <SPI.h>


//teclado
byte linha [] ={22,24,26,28}; // conecçoes
char teclas [4] =  {'2', '1','4', '3'};

//variable recevied pin from raspberry
String passere="";
//variable recevied pin from keypad teclad
String passe ="";
//var tem
int cc =0;

//display
#define pinBotoes A15  //Botao

#define pinRs 39 //pino rs 8
#define pinEn 41 //pino enable 9
#define pinD4 31 //4
#define pinD5 33 //5
#define pinD6 35 //6 
#define pinD7 37 //7

LiquidCrystal lcd(pinRs, pinEn, pinD4, pinD5, pinD6, pinD7);
 
//wifi
char ssid[] = "iPhone de Pedrilson";
char pass[] = "Pedrilson";
int status = WL_IDLE_STATUS;

IPAddress server(172,20,10,7); //ip server
WiFiClient client; //client

void setup() {
 
  Serial.begin(9600);
  lcd.begin(16,2);
  status = WiFi.begin(ssid, pass);
 
 
  // wifi shield not present
  if(WiFi.status() == WL_NO_SHIELD) {

    lcd.print("not present");
    // don't continue:
    while (true);
   }


    // if you're not connected, stop here:
   if( status != WL_CONNECTED) {
    lcd.print("not connect");
    
    while(true);
   }
  // if you are connected, print  connect to network
  else {
    lcd.print("Connect to network");
    Serial.println("Connected to network");
    
  }
  
  //teclado
   pinMode(22, INPUT_PULLUP);
   pinMode(24, INPUT_PULLUP);
   pinMode(26, INPUT_PULLUP);
   pinMode(28, INPUT_PULLUP);

   
}

void loop() {
  
  //read the button
  int b= analogRead(pinBotoes);
  
  delay(100);
  //if click in button, print msg "Put the password"
  if(b < 800){
    
    //get a request
    request();
    int iv=0; // variable temp
    String lin =""; //variable temp

    //receive pin 
    while (client.available() || true) {
      String c = client.readStringUntil('\n');
      Serial.println(c);
      lin =c;
      iv++;
      passere=lin.substring(30, 34); //receive pin from raspberry
        
      if(iv ==9){
        iv=0;
        break;
      }
      
    }

    Serial.println(passere);
    Serial.println(b);
    passe="";
    cc=0;
    lcd.clear();
    delay(100);
    //c++;
    lcd.clear();
    lcd.print("Put the password:");
    lcd.setCursor(0,1);
    //delay(1000);  

  }
   //processament of input from keypad teclad and verification the pin 
   for(int i=0; i<4; i++) {
      if(digitalRead(linha[i]) == LOW){
     
        if(cc < 4) {
          passe += teclas[i];
          //Serial.println(teclas[i]);
          lcd.print("*");
          cc++;
        }
        if(cc == 4){
          lcd.clear();
          //verification the password
          if(passere.equals(passe)){
            //password correct
            lcd.print("Correct!");
            lcd.setCursor(0,1);
            lcd.print(passe);
          }
          else { 
            //password incorrect
            lcd.clear();
            lcd.print("Incorrect, put again:");
            lcd.setCursor(0,1);
            passe="";
            cc=0;
          }
        }
      }
  }
}

 //function make a get request
 void request() {
    client.stop();
    if(client.connect(server, 8002)) {
      Serial.println("Connect to server");
      client.println("GET /checkOut?nextClient=0 HTTP/1.1");
      client.println("Host: 172.20.10.3");
      client.println("Connection: close");
      client.println();
    }
    else {
      Serial.println("Not connect server");
    }
 }
