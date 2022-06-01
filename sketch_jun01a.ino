#include <LiquidCrystal.h>
#include <SPI.h>

#include <WiFi.h>

//internet usa 13-10 e 7 usaso para ligaçao 4para sd

//teclado

byte linha [] ={0,1,2,3};
char teclas [4] =  {'2', '1','4', '3'};

String passere="1234";
String passe ="";
int cc =0;

//display
#define pinBotoes A0

#define pinRs 8 //pino rs
#define pinEn 9 //pino enable
#define pinD4 4
#define pinD5 5
#define pinD6 6
#define pinD7 7

LiquidCrystal lcd(pinRs, pinEn, pinD4, pinD5, pinD6, pinD7);
 
//wifi
char ssid[] = "iPhone de Pedrilson";
char pass[] = "Pedrilson";
int status = WL_IDLE_STATUS;
int count =30;
WiFiServer server(80);

void setup() {
 
  //Serial.begin(9600);
  lcd.begin(16,2);
  //Serial.println("Attempting to connect to WPA network...");

  status = WiFi.begin(ssid, pass);

 // Serial.println("Scanning available networks...");

  //listNetworks();
  if (WiFi.status() == WL_NO_SHIELD) {

    //Serial.println("WiFi shield not present");

    // don't continue:

    //while (true);
    lcd.print("not present");

  }

  /*String fv = WiFi.firmwareVersion();

  if (fv != "1.1.0") {

    Serial.println("Please upgrade the firmware");

  }*/

  // if you're not connected, stop here:

 if ( status != WL_CONNECTED) {

    //Serial.println("Couldn't get a wifi connection");
    lcd.print("nconect");
    while(true);

  }

  // if you are connected, print out info about the connection:

  else {
    lcd.print("C0nect");
    //Serial.println("Connected to network");

  }
  
  //teclado
  /*for(int i=0; i<4; i++)
    pinMode(linha[i], INPUT_PULLUP);*/

   pinMode(0, INPUT_PULLUP);
   pinMode(1, INPUT_PULLUP);
   pinMode(2, INPUT_PULLUP);
   pinMode(3, INPUT_PULLUP);

   //lcd.print("OK");

}


void loop() {
  //Serial.println("ENTROU");
  //WiFiClient client = server.available();
  
  int b= analogRead(pinBotoes);
  //Serial.println(b);
  delay(100);
  if(b < 800){
    passe="";
    cc=0;
    lcd.clear();
    delay(100);
    //c++;
    lcd.clear();
    //lcd.print("pqrstuvwsyz");
    lcd.print("Put passw0rd");
    lcd.setCursor(0,1);
    //delay(1000);  

  }
    
   for(int i=0; i<4; i++) {
      if(digitalRead(linha[i]) == LOW){
        //Serial.println(teclas[i]);
        if(cc < 4){
          passe += teclas[i];
          lcd.print("*");
          cc++;
      
          
          //lcd.println(teclas[i]);
        }
        //Serial.println(passe);
        //lcd.clear();
        //lcd.println(passe);
        if(cc == 4){
          lcd.clear();
          if(passere.equals(passe)){
            lcd.print("C0rrect!");
            //lcd.print("TRUE");
            lcd.setCursor(0,1);
            lcd.print(passe);
            //Serial.print("PASSE:");
            //Serial.println(passe);
          }
          else {
            lcd.clear();
            lcd.print("Inc0rrect" + passe);
            lcd.setCursor(0,1);
            //lcd.setCursor(0,1);
            //lcd.print(passe);
            //Serial.print("PASSE:");
            //Serial.println(passe);
            passe="";
            cc=0;
          }
        }
      }
  }

  
  /*if(passere.equals(passe))
      lcd.println("CORRECT!");
    else
      lcd.println("INCORRECT");*/

  
  /*if (client) {

    Serial.println("new client");

    // an http request ends with a blank line

    bool currentLineIsBlank = true;

    while (client.connected()) {

      if (client.available()) {

        char c = client.read();

        Serial.write(c);

        // if you've gotten to the end of the line (received a newline

        // character) and the line is blank, the http request has ended,

        // so you can send a reply

        if (c == '\n' && currentLineIsBlank) {

          // send a standard http response header

          client.println("HTTP/1.1 200 OK");

          client.println("Content-Type: text/html");

          client.println("Connection: close");  // the connection will be closed after completion of the response

          client.println("Refresh: 5");  // refresh the page automatically every 5 sec

          client.println();

          client.println("<!DOCTYPE HTML>");

          client.println("<html>");

          client.print("COUNT = ");
          client.println(count);

          client.println("</html>");

          break;

        }

        if (c == '\n') {

          // you're starting a new line

          currentLineIsBlank = true;

        } 
        else if (c != '\r') {

          // you've gotten a character on the current line

          currentLineIsBlank = false;
        }

      }
    }
  }

   */    
}


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





//Serial.println("Count = " + count);
 
  // listen for incoming clients
 /* EthernetClient client = server.available();
  if (client) {
    Serial.println("new client");
    // an http request ends with a blank line
    boolean currentLineIsBlank = true;
    while (client.connected()) {
      if (client.available()) {
        char c = client.read();
        Serial.write(c);
        // if you've gotten to the end of the line (received a newline
        // character) and the line is blank, the http request has ended,
        // so you can send a reply
        if (c == '\n' && currentLineIsBlank) {
          // send a standard http response header
          client.println("HTTP/1.1 200 OK");
          client.println("Content-Type: text/html");
          client.println("Connection: close");  // the connection will be closed after completion of the response
          client.println("Refresh: 5");  // refresh the page automatically every 5 sec
          client.println();
          client.println("<!DOCTYPE HTML>");
          client.println("<html>");
          client.println("<head>");
          // output the value of each analog input pin
          /*for (int analogChannel = 0; analogChannel < 6; analogChannel++) {
            int sensorReading = analogRead(analogChannel);
            client.print("analog input ");
            client.print(analogChannel);
            client.print(" is ");
            client.print(sensorReading);
            client.println("<br />");
          }*/
         /* client.println("<title>FILA DE ESPERA</title>");
          client.println("</head>");
          client.println("<body>");
          client.print("Count = ");
          client.println(count);
          client.println("</body>");
          client.println("</html>");
          break;
        }
        if (c == '\n') {
          // you're starting a new line
          currentLineIsBlank = true;
        } else if (c != '\r') {
          // you've gotten a character on the current line
          currentLineIsBlank = false;
        }
      }
    }
    // give the web browser time to receive the data
    delay(1);
    // close the connection:
    client.stop();
    Serial.println("client disconnected");
  }
  }*/
