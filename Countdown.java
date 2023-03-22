package lab.Countdown;

// Projekt Countdown 23.12.2020 Eva Geltinger
import hdm.shared.Toolbox;
import processing.core.PApplet;
import processing.core.PImage;
//import processing.core.PImage;

public class Countdown extends PApplet {

	private static final float LOGICAL_WIDTH = 1680;

	public static void main(String[] args) {

		PApplet.main(new String[] { Countdown.class.getName() });

	}

	public void settings() {
		size(1680, 1050); // setzt Fenstergröße auf 1680x1050 Pixel
		fullScreen(); // Fenster wird im Fullscreen angezeigt

	}

	public void setup() {
		background(0);
		stroke(255);
		textSize(100);

	}

	char doppelpunkt = ':';
	int time;
	int wait = 1000;
	int currentSeconds = second();
	int currentMinutes = minute();
	int currentHours = hour();
	int seconds = 59 - currentSeconds;

	int currentAllMinutes = currentMinutes + currentHours * 60 + 1;

	int minutesDifference;
	int minutesToHours;
	int verbleibendeMinutes;

	int minutes = verbleibendeMinutes();
	int hours = verbleibendeHours();
	float lengthCountdownInSeconds;
	float lengthCountdownInMinutes;

	final int FIRST_BLOCK = 495; // 8:15 Uhr
	final int SECOND_BLOCK = 600; // 10:00 Uhr
	final int THIRD_BLOCK = 705; // 11:45 Uhr
	final int FOURTH_BLOCK = 855; // 14:15 Uhr
	final int FIFTH_BLOCK = 960; // 16:00 Uhr
	final int SIXTH_BLOCK = 1065; // 17:45 Uhr
	final int SEVENTH_BLOCK = 1170; // 19:30 Uhr
	final int ONE_DAY = 1440; // Minuten an einem Tag

	int xWertVonImageRedStanding = 50;
	int xWertVonImageRedRunning = 51;

	public void draw() {
		scale(width / LOGICAL_WIDTH); // Fenster wird an Bildschrimgröße angepasst
		background(0); // Hintergrund schwarz

		PImage photoHdM = loadImage("lab/Countdown/image/hdmLogo.jpg");
		image(photoHdM, 100, 100);
		countdownMinAndSec();
		minuteOrSecondOrHourKleinerOderGroesserNull();
		cheatmodus();
		ladebalken();
		lastThirtySecondsToTenSeconds();
		lastTenSeconds();
		runnningSound();
	}

	public void countdownMinAndSec() {

		if (millis() - time >= wait && seconds > -1 && !(seconds == 0 && minutes == 0 && hours == 0)) {
			/*
			 * nachdem eine sekunde um ist und die sekunde größer 0 ist, soll eins abgezogen
			 * werden, sodass pro sec eins runter, Minuten werden um eins heruntergezählt,
			 * wenn Sekunden gleich -1 , Stunden werden um 1 heruntergezählt, wenn Minuten
			 * bei -1 sind , es wird nicht meht heruntergezählt sobald Stunden, Minuten und
			 * Sekunden 0 sind
			 */
			time = millis();
			seconds -= 1; // runterzählen der sekunden von 59 an
			if (seconds == -1 && minutes > -1) {
				time = millis() / 60;
				minutes -= 1;
			}
			if (seconds == -1) {
				seconds = 60;
			}
			if (minutes == -1) {
				minutes = 60;
			}
			if (minutes == -1 && hours > 1) {
				hours -= 1;
			}
		}

	}

	public boolean cheatmodus() {
		if (keyPressed) {
			switch (key) {
			case ' ': // bei drücken der Leertaste werden die letzten 30 Sekunden des Countdowns
						// abgespielt
				seconds = 30;
				minutes = 0;
				hours = 0;
				break;
			case '5': // bei drücken der Taste 5 wird der Countdown auf fünf Minuten gesetzt
				seconds = 0;
				minutes = 5;
				hours = 0;
				break;
			case '1': // bei drücken der Taste 1 wird der Countdown auf eine Minute gesetzt
				seconds = 0;
				minutes = 1;
				hours = 0;
			}
		}
		return false;

	}

	public void minuteOrSecondOrHourKleinerOderGroesserNull() { // Methode zur textuellen Ausgabe des Countdowns, dabei
																// wird beachtet das bei zB 5 Minuten 05 steht
		textSize(120);
		fill(255);
		countdownBlinken();
		if (seconds >= 10) {

			if (minutes >= 10) {
				if (hours >= 10) {
					text(hours + doppelpunkt + minutes + doppelpunkt + seconds, 600, 500);
				} else if (hours < 10) {
					text("0" + hours + doppelpunkt + minutes + doppelpunkt + seconds, 600, 500);
				}
			} else if (minutes < 10) {
				if (hours >= 10) {
					text(hours + doppelpunkt + "0" + minutes + doppelpunkt + seconds, 600, 500);
				} else if (hours < 10) {
					text("0" + hours + doppelpunkt + "0" + minutes + doppelpunkt + seconds, 600, 500);
				}
			}

		} else if (seconds < 10) {
			if (minutes >= 10) {
				if (hours >= 10) {
					text(hours + doppelpunkt + minutes + doppelpunkt + "0" + seconds, 600, 500);
				} else if (hours < 10) {
					text("0" + hours + doppelpunkt + minutes + doppelpunkt + "0" + seconds, 600, 500);
				}
			} else if (minutes < 10) {
				if (hours >= 10) {
					text(hours + doppelpunkt + "0" + minutes + doppelpunkt + "0" + seconds, 600, 500);
				} else {
					text("0" + hours + doppelpunkt + "0" + minutes + doppelpunkt + "0" + seconds, 600, 500);
				}
			}
		}
	}

	public int verbleibendeMinutes() {
		/*
		 * Methode um die verbleibenden Minuten bis der folgende Block erreicht wird zu
		 * berechnen, die Minuten werden zurück gegeben (ohne die Stunden)
		 */

		if (currentAllMinutes < 495 && currentAllMinutes > 0) {
			minutesDifference = FIRST_BLOCK - currentAllMinutes;
		} else if (currentAllMinutes >= 495 && currentAllMinutes < 600) {
			minutesDifference = SECOND_BLOCK - currentAllMinutes;
		} else if (currentAllMinutes >= 600 && currentAllMinutes < 705) {
			minutesDifference = THIRD_BLOCK - currentAllMinutes;
		} else if (currentAllMinutes >= 705 && currentAllMinutes < 855) {
			minutesDifference = FOURTH_BLOCK - currentAllMinutes;
		} else if (currentAllMinutes >= 855 && currentAllMinutes < 960) {
			minutesDifference = FIFTH_BLOCK - currentAllMinutes;
		} else if (currentAllMinutes >= 960 && currentAllMinutes < 1065) {
			minutesDifference = SIXTH_BLOCK - currentAllMinutes;
		} else if (currentAllMinutes >= 1065 && currentAllMinutes < 1170) {
			minutesDifference = SEVENTH_BLOCK - currentAllMinutes;
		}

		minutesToHours = minutesDifference / 60;
		verbleibendeMinutes = minutesDifference - minutesToHours * 60;

		minutes = verbleibendeMinutes;

		return minutes;

	}

	public int verbleibendeHours() {
		/*
		 * Methode um die verbleibenden Stunden bis der folgende Block erreicht wird zu
		 * berechnen, die Stunden werden zurück gegeben (ohne die Minuten)
		 */
		if (currentAllMinutes < 495 && currentAllMinutes > 0) {
			minutesDifference = FIRST_BLOCK - currentAllMinutes;
		} else if (currentAllMinutes >= 495 && currentAllMinutes < 600) {
			minutesDifference = SECOND_BLOCK - currentAllMinutes;
		} else if (currentAllMinutes >= 600 && currentAllMinutes < 705) {
			minutesDifference = THIRD_BLOCK - currentAllMinutes;
		} else if (currentAllMinutes >= 705 && currentAllMinutes < 855) {
			minutesDifference = FOURTH_BLOCK - currentAllMinutes;
		} else if (currentAllMinutes >= 855 && currentAllMinutes < 960) {
			minutesDifference = FIFTH_BLOCK - currentAllMinutes;
		} else if (currentAllMinutes >= 960 && currentAllMinutes < 1065) {
			minutesDifference = SIXTH_BLOCK - currentAllMinutes;
		} else if (currentAllMinutes >= 1065 && currentAllMinutes < 1170) {
			minutesDifference = SEVENTH_BLOCK - currentAllMinutes;
		} else if (currentAllMinutes >= 1170) { // wenn die Uhrzeit nach dem letzten Block noch am gleichen Tag ist,
												// wird die Zeit bis zum ersten BLock 8:15 berechnet
			minutesDifference = currentAllMinutes - (currentAllMinutes - FIRST_BLOCK) + (ONE_DAY - currentAllMinutes);
		}

		minutesToHours = minutesDifference / 60;
		verbleibendeMinutes = minutesDifference - minutesToHours * 60;

		minutes = verbleibendeMinutes;
		hours = minutesToHours;
		return hours;

	}

	public void lastThirtySecondsToTenSeconds() {
		/*
		 * in den letzten dreizig Sekunden des Countdowns (bis 10 Sekunden) sollen zwei
		 * Bilder geladen werden, die sich im Sekundentakt abwechseln, sodass es wie
		 * Bewegung aussieht
		 */
		if (seconds <= 30 && minutes == 0 && hours == 0 && seconds > 10) {

			if (seconds % 2 == 0) {
				fill(128, 241, 26);
				rect(0, 600, 2000, 300); // rect(x, y, w, h) 700 600
				fill(255);
				PImage redStanding = loadImage("lab/Countdown/image/redStanding.png");
				image(redStanding, 50, 600);

			} else {
				fill(128, 241, 26);
				rect(0, 600, 2000, 300); // rect(x, y, w, h) 700 600
				fill(255);
				PImage redRunning = loadImage("lab/Countdown/image/redRunning.png");
				image(redRunning, 50, 600);

			}
		}
	}

	public void lastTenSeconds() {
		/*
		 * in den letzten 10 Sekunden soll die Bewegung auf einer Stelle aus der Methode
		 * lastThirtySecondsToTenSeconds() in x Richtung verschoben werden
		 */
		if (seconds <= 10 && minutes == 0 && hours == 0 && seconds > 1) {
			xWertVonImageRedStanding += 2;
			xWertVonImageRedRunning += 2;
			fill(128, 241, 26);
			rect(0, 600, 2000, 300); // rect(x, y, w, h) 700 600
			fill(255);
			if (seconds % 2 == 0) {

				PImage redStanding = loadImage("lab/Countdown/image/redStanding.png");
				image(redStanding, xWertVonImageRedStanding, 600);
				PImage bbb1 = loadImage("lab/Countdown/image/BigBlueButton1.png");
				image(bbb1, 740, 600);
			} else if (seconds % 2 != 0) {

				PImage redRunning = loadImage("lab/Countdown/image/redRunning.png");
				image(redRunning, xWertVonImageRedRunning, 600);
				PImage bbb1 = loadImage("lab/Countdown/image/BigBlueButton1.png");
				image(bbb1, 740, 600);
			}

			// bei erreichen der 0 soll Bild eingefügt werden
		} else if (minutes == 0 && hours == 0 && seconds == 0) {
			fill(128, 241, 26);
			rect(0, 600, 2000, 300); // rect(x, y, w, h) 700 600
			fill(255);

			PImage bbbVorlesungsBeginn = loadImage("lab/Countdown/image/BigBlueButton2.png");
			image(bbbVorlesungsBeginn, 740, 600);

			// bei erreichen der letzten Sekunde soll Bild eingefügt werden
		} else if (minutes == 0 && hours == 0 && seconds == 1) {
			fill(128, 241, 26);
			rect(0, 600, 2000, 300); // rect(x, y, w, h) 700 600
			fill(255);

			PImage redButton = loadImage("lab/Countdown/image/redButton.png");
			image(redButton, 0, 0);
		}
	}

	public void ladebalken() {
		/*
		 * die Methode ladebalken() gibt ein Rechteck aus, was sich in -x Richtung
		 * verkleinert, wenn Sekunden, Minuten, Stunden heruntergezählt werden
		 * 
		 * währenddessen werden verschiedene Textausgaben je nach verbleibenden
		 * Minuten/Sekunden ausgegeben (siehe READ ME Datei)
		 */

		lengthCountdownInSeconds = hours * 3600 + minutes * 60 + seconds;
		lengthCountdownInMinutes = lengthCountdownInSeconds / 60;
		if (lengthCountdownInSeconds > 0) {

			fill(255, 0, 0);
			noStroke();
			rect(100, 900, (lengthCountdownInMinutes + lengthCountdownInSeconds) / width * 1000, 40); // rect(x,
		}
		if (lengthCountdownInMinutes > 60) {
			fill(0, 255, 0);
			textSize(18);
			text("Noch soooooooooooooooooooooooooooooooooooooooooo lange warten...", 110, 980);
		}
		if (lengthCountdownInMinutes >= 10 && lengthCountdownInMinutes <= 60) {
			fill(0, 255, 0);
			textSize(18);
			text("Nicht mehr ganz soooooooooooooooooooooooooooooooooooooooooo lange warten...", 110, 980);
		}
		if (lengthCountdownInMinutes < 10 && lengthCountdownInMinutes > 1) {
			fill(0, 255, 0);
			textSize(18);
			text("In wenigen Minuten beginnt die Vorlesung, macht euch bereit...", 110, 980);
		}
		if (lengthCountdownInSeconds < 60 && lengthCountdownInSeconds >= 30) {
			fill(0, 255, 0);
			textSize(18);
			text("Die letzte Minute hat begonnen...", 110, 980);
		}
		if (lengthCountdownInSeconds < 30 && lengthCountdownInSeconds > 0) {
			fill(0, 255, 0);
			textSize(18);
			text("Die Zeit rennt!!!", 110, 980);
		}
	}

	public void countdownBlinken() {
		// Methode erzeugt ein Farbwechsel von weiß nach grün, wenn die Sekunden gerade
		// sind
		if (seconds % 2 == 0) {
			fill(0, 255, 0);

		}
	}

	boolean playedSoundOnce = false;
	boolean secondPlayedSoundOnce = false;
	boolean thirdPlayedSoundOnce = false;
	boolean fourthPlayedSoundOnce = false;

	public void runnningSound() {
		/*
		 * gibt Audiodateien aus, je nach verbleibenden Sekunden Sound wird nur einmal
		 * abspielt werden, da wenn abgespielt wird die boolean Variable auf true
		 * gesetzt
		 */
		if (playedSoundOnce == false && minutes == 0 && hours == 0 && seconds == 30) {
			playedSoundOnce = true;
			Toolbox.playAudioFileAsychronously("/lab/Countdown/audioFiles/runningSound.wav");

		}
		if (thirdPlayedSoundOnce == false && minutes == 0 && hours == 0 && seconds == 20) {
			thirdPlayedSoundOnce = true;
			Toolbox.playAudioFileAsychronously("/lab/Countdown/audioFiles/runningSound.wav");
		}
		if (fourthPlayedSoundOnce == false && minutes == 0 && hours == 0 && seconds == 10) {
			fourthPlayedSoundOnce = true;
			Toolbox.playAudioFileAsychronously("/lab/Countdown/audioFiles/runningSound.wav");
		}

		if (secondPlayedSoundOnce == false && minutes == 0 && hours == 0 && seconds == 1) {
			secondPlayedSoundOnce = true;
			Toolbox.playAudioFileAsychronously("/lab/Countdown/audioFiles/buzzer.wav");
		}

	}

}
