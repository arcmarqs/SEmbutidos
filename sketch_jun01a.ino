#include <LiquidCrystal.h>
#include <SPI.h>

#include <WiFi.h>


//teclado

byte linha [] ={22,24,26,28}; // conecçoes
char teclas [4] =  {'2', '1','4', '3'};

//password recevied
String passere="1234";
//password teclado
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
char ssid[] = "iPhone de Pedrilson";//"embutidos";
char pass[] = "Pedrilson";//"TouNess@";
int status = WL_IDLE_STATUS;
int count =30;
WiFiServer server('194.210.211.35');

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
    /*
    WiFiClient client = server.available();
    client.Write(count);

    */
    //enviar o sinal ao raspberry count =(nº de arduino)
    server.write(count);
    //pin recebido do raspberry
    passere = request();
    


    
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

  String  request(){
    String s = "";
    WiFiClient client = server.available();
    while (client == true) {
      char c = client.read();
      Serial.println(c);
      s +=c;
      //server.write(client.read());
    }
    return s;
  }
  


//list the networks 
void listNetworks() {

  // scan for nearby networks:

  Serial.println("** Scan Networks **");

  byte numSsid = WiFi.scanNetworks();

  // print the list of networks seen:

  Serial.print("number of available networks:");

  Serial.println(numSsid);

  // print the network number and name for each network found:

  for (int thisNet = 0; thisNet<numSsid; thisNet++) {

    Serial.print(thisNet);

    Serial.print(") ");

    Serial.print(WiFi.SSID(thisNet));

    Serial.print("\tSignal: ");

    Serial.print(WiFi.RSSI(thisNet));

    Serial.print(" dBm");

    Serial.print("\tEncryption: ");

    Serial.println(WiFi.encryptionType(thisNet));

  }
}

