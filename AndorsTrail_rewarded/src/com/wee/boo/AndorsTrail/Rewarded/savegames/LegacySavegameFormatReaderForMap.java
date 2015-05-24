/*
 * Copyright� 2015 Yaniv Bokobza
 * Based on Andor's Trail open source game (GPLv2)
 *
 * This file is part of Andor's Trail - Rewarded.
 *
 * Andor's Trail - Rewarded is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Andor's Trail - Rewarded is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Andor's Trail - Rewarded.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.wee.boo.AndorsTrail.Rewarded.savegames;

public final class LegacySavegameFormatReaderForMap {
	public static String getMapnameFromIndex(int index) {
		/*
		 * Before v0.7.0 (before fileversion 35), all maps were stored in the savegame file
		 * in a specific order, one after the other. Because of that, the ordering had significance
		 * when loading the files ("home.tmx" was expected to come first, for example.
		 * We've now moved away from that ordering, but still need to be able to load old savegame files
		 * that still use that ordering. Hence this list of mapping indexes to which map it means.
		 */
		switch (index) {
			case 0: return "home";
			case 1: return "crossglen";
			case 2: return "crossglen_farmhouse";
			case 3: return "crossglen_farmhouse_basement";
			case 4: return "crossglen_hall";
			case 5: return "crossglen_smith";
			case 6: return "crossglen_cave";
			case 7: return "wild1";
			case 8: return "wild2";
			case 9: return "wild3";
			case 10: return "jan_pitcave1";
			case 11: return "jan_pitcave2";
			case 12: return "jan_pitcave3";
			case 13: return "fallhaven_nw";
			case 14: return "snakecave1";
			case 15: return "snakecave2";
			case 16: return "snakecave3";
			case 17: return "wild4";
			case 18: return "hauntedhouse1";
			case 19: return "hauntedhouse2";
			case 20: return "fallhaven_ne";
			case 21: return "fallhaven_church";
			case 22: return "fallhaven_barn";
			case 23: return "fallhaven_potions";
			case 24: return "fallhaven_gravedigger";
			case 25: return "fallhaven_clothes";
			case 26: return "fallhaven_arcir";
			case 27: return "fallhaven_arcir_basement";
			case 28: return "fallhaven_athamyr";
			case 29: return "fallhaven_rigmor";
			case 30: return "fallhaven_tavern";
			case 31: return "fallhaven_prison";
			case 32: return "fallhaven_derelict";
			case 33: return "fallhaven_nocmar";
			case 34: return "catacombs1";
			case 35: return "catacombs2";
			case 36: return "catacombs3";
			case 37: return "catacombs4";
			case 38: return "hauntedhouse3";
			case 39: return "hauntedhouse4";
			case 40: return "fallhaven_sw";
			case 41: return "wild5";
			case 42: return "wild6";
			case 43: return "wild6_house";
			case 44: return "wild7";
			case 45: return "wild8";
			case 46: return "wild9";
			case 47: return "wild10";
			case 48: return "flagstone0";
			case 49: return "flagstone_inner";
			case 50: return "flagstone_upper";
			case 51: return "flagstone1";
			case 52: return "flagstone2";
			case 53: return "flagstone3";
			case 54: return "flagstone4";
			case 55: return "wild11";
			case 56: return "wild12";
			case 57: return "wild11_clearing";
			case 58: return "clearing_level1";
			case 59: return "clearing_level2";
			case 60: return "fallhaven_se";
			case 61: return "fallhaven_lumberjack";
			case 62: return "fallhaven_alaun";
			case 63: return "fallhaven_storage";
			case 64: return "fallhaven_farmer";
			case 65: return "wild13";
			case 66: return "wild14";
			case 67: return "wild14_cave";
			case 68: return "wild14_clearing";
			case 69: return "wild15";
			case 70: return "wild15_house";
			case 71: return "road1";
			case 72: return "foaming_flask";
			case 73: return "fallhaven_derelict2";
			case 74: return "vilegard_n";
			case 75: return "vilegard_s";
			case 76: return "vilegard_sw";
			case 77: return "vilegard_ogam";
			case 78: return "vilegard_chapel";
			case 79: return "vilegard_tavern";
			case 80: return "vilegard_armorer";
			case 81: return "vilegard_smith";
			case 82: return "vilegard_wrye";
			case 83: return "vilegard_kaori";
			case 84: return "vilegard_erttu";
			case 85: return "road2";
			case 86: return "road3";
			case 87: return "road4";
			case 88: return "road4_gargoylecave";
			case 89: return "road5";
			case 90: return "road5_house";
			case 91: return "gargoylecave1";
			case 92: return "gargoylecave2";
			case 93: return "gargoylecave3";
			case 94: return "gargoylecave4";
			case 95: return "blackwater_mountain0";
			case 96: return "blackwater_mountain1";
			case 97: return "blackwater_mountain2";
			case 98: return "blackwater_mountain3";
			case 99: return "blackwater_mountain4";
			case 100: return "blackwater_mountain5";
			case 101: return "blackwater_mountain6";
			case 102: return "blackwater_mountain7";
			case 103: return "blackwater_mountain8";
			case 104: return "blackwater_mountain9";
			case 105: return "blackwater_mountain10";
			case 106: return "blackwater_mountain11";
			case 107: return "blackwater_mountain12";
			case 108: return "blackwater_mountain13";
			case 109: return "blackwater_mountain14";
			case 110: return "blackwater_mountain15";
			case 111: return "blackwater_mountain16";
			case 112: return "blackwater_mountain17";
			case 113: return "blackwater_mountain18";
			case 114: return "blackwater_mountain19";
			case 115: return "blackwater_mountain20";
			case 116: return "blackwater_mountain21";
			case 117: return "blackwater_mountain22";
			case 118: return "blackwater_mountain23";
			case 119: return "blackwater_mountain24";
			case 120: return "blackwater_mountain25";
			case 121: return "blackwater_mountain26";
			case 122: return "blackwater_mountain27";
			case 123: return "blackwater_mountain28";
			case 124: return "blackwater_mountain29";
			case 125: return "blackwater_mountain30";
			case 126: return "blackwater_mountain31";
			case 127: return "blackwater_mountain32";
			case 128: return "blackwater_mountain33";
			case 129: return "blackwater_mountain34";
			case 130: return "blackwater_mountain35";
			case 131: return "blackwater_mountain36";
			case 132: return "blackwater_mountain37";
			case 133: return "blackwater_mountain38";
			case 134: return "blackwater_mountain39";
			case 135: return "blackwater_mountain40";
			case 136: return "blackwater_mountain41";
			case 137: return "blackwater_mountain42";
			case 138: return "blackwater_mountain43";
			case 139: return "blackwater_mountain44";
			case 140: return "blackwater_mountain45";
			case 141: return "blackwater_mountain46";
			case 142: return "blackwater_mountain47";
			case 143: return "blackwater_mountain48";
			case 144: return "blackwater_mountain49";
			case 145: return "blackwater_mountain50";
			case 146: return "blackwater_mountain51";
			case 147: return "blackwater_mountain52";
			case 148: return "wild0";
			case 149: return "crossroads";
			case 150: return "fields0";
			case 151: return "fields1";
			case 152: return "fields2";
			case 153: return "fields3";
			case 154: return "fields4";
			case 155: return "fields5";
			case 156: return "fields6";
			case 157: return "fields7";
			case 158: return "fields8";
			case 159: return "fields9";
			case 160: return "fields10";
			case 161: return "fields11";
			case 162: return "fields12";
			case 163: return "houseatcrossroads0";
			case 164: return "houseatcrossroads1";
			case 165: return "houseatcrossroads2";
			case 166: return "houseatcrossroads3";
			case 167: return "houseatcrossroads4";
			case 168: return "houseatcrossroads5";
			case 169: return "loneford1";
			case 170: return "loneford2";
			case 171: return "loneford3";
			case 172: return "loneford4";
			case 173: return "loneford5";
			case 174: return "loneford6";
			case 175: return "loneford7";
			case 176: return "loneford8";
			case 177: return "loneford9";
			case 178: return "loneford10";
			case 179: return "roadbeforecrossroads";
			case 180: return "roadtocarntower0";
			case 181: return "roadtocarntower1";
			case 182: return "roadtocarntower2";
			case 183: return "woodcave0";
			case 184: return "woodcave1";
			case 185: return "wild16";
			case 186: return "wild17";
			case 187: return "gapfiller1";
			case 188: return "gapfiller3";
			case 189: return "gapfiller4";
			case 190: return "waterway0";
			case 191: return "waterway1";
			case 192: return "waterway2";
			case 193: return "waterway3";
			case 194: return "waterwayhouse";
			case 195: return "waterwayextention";
			case 196: return "pwcave0";
			case 197: return "pwcave1";
			case 198: return "pwcave2";
			case 199: return "pwcave2a";
			case 200: return "pwcave3";
			case 201: return "pwcave4";
			case 202: return "waterway4";
			case 203: return "waterway5";
			case 204: return "waterway6";
			case 205: return "waterway7";
			case 206: return "waterway8";
			case 207: return "waterway9";
			case 208: return "waterway10";
			case 209: return "waterway11_east";
			case 210: return "waterway11";
			case 211: return "waterway12";
			case 212: return "waterway13";
			case 213: return "waterway14";
			case 214: return "waterway15";
			case 215: return "waterwaycave";
			case 216: return "mountaincave0";
			case 217: return "mountaincave1";
			case 218: return "mountaincave2";
			case 219: return "mountaincave3";
			case 220: return "mountainlake0";
			case 221: return "mountainlake1";
			case 222: return "mountainlake2";
			case 223: return "mountainlake3";
			case 224: return "mountainlake4";
			case 225: return "mountainlake5";
			case 226: return "mountainlake6";
			case 227: return "mountainlake7";
			case 228: return "mountainlake8";
			case 229: return "mountainlake9";
			case 230: return "mountainlake10";
			case 231: return "mountainlake10a";
			case 232: return "mountainlake11";
			case 233: return "mountainlake12";
			case 234: return "mountainlake13";
			case 235: return "mountainlake13a";
			case 236: return "remgard0";
			case 237: return "remgard1";
			case 238: return "remgard2";
			case 239: return "remgard3";
			case 240: return "remgard4";
			case 241: return "remgard_armour";
			case 242: return "remgard_barn";
			case 243: return "remgard_church";
			case 244: return "remgard_clothes";
			case 245: return "remgard_farmer1";
			case 246: return "remgard_farmer2";
			case 247: return "remgard_farmer3";
			case 248: return "remgard_prison";
			case 249: return "remgard_school";
			case 250: return "remgard_tavern0";
			case 251: return "remgard_tavern1";
			case 252: return "remgard_villager1";
			case 253: return "remgard_villager2";
			case 254: return "remgard_villager3";
			case 255: return "remgard_villager4";
			case 256: return "remgard_villager5";
			case 257: return "remgard_weapon";
			case 258: return "waytobrimhaven0";
			case 259: return "waytobrimhaven1";
			case 260: return "waytobrimhaven2";
			case 261: return "waytobrimhaven3";
			case 262: return "waytobrimhavencave0";
			case 263: return "waytobrimhavencave1a";
			case 264: return "waytobrimhavencave1";
			case 265: return "waytobrimhavencave2";
			case 266: return "waytobrimhavencave3a";
			case 267: return "waytobrimhavencave3b";
			case 268: return "waytobrimhavencave3";
			case 269: return "waytobrimhavencave4";
			case 270: return "waytolake0";
			case 271: return "waytolake1";
			case 272: return "waytolake2";
			case 273: return "waytolake3";
			case 274: return "waytolake4";
			case 275: return "waytolake5";
			case 276: return "waytomountaincave0";
			case 277: return "waytomountaincave1";
			case 278: return "waytomountaincave2";
			case 279: return "lonelyhouse0";
			case 280: return "lonelyhouse1";
			case 281: return "wild16_cave";
			case 282: return "gapfiller2";
		}
		return null;
	}
}