# Verium

## What's Verium
Verium is spigot plugin, that aims to bring different kinds of challenges to minecraft.
Because it is a server plugin, you can also play these challenges with your friends.

## Current state
Veriums development has just started, that's why the current supported features are very limited.
### Current supported features:

 - A timer that can be used with `/timer` to measure how well you performed and beat your last record 🚀
 - `/reset` to reset the current world and retry 🔁
 - With `/pause` and `/resume`, you can pause the timer, active challenges, gamerule and goals, and set everyone into spectator
   
#### Challenges (can be managed with `/challenges`):
- No Death: Challenge will fail if a player dies 💀
- No Crafting: Disables the use of crafting tables 🛠️
- Wolf Survive: Every player receives a wolf that must survive at all cost 🦴
- No Fall Damage: Receiving fall damage, even 1HP, will end the challenge 🪶

#### Gamerules (can be managed with `/gamerules`):
- No hunger: disable the ability to lose hunger 🍗
- PvP: Enables the possibility to hit and kill other players ⚔️
- UHC: Disables natural regeneration of HP ❤️
- UUHC: Disables every type of regeneration ❤️‍🩹
- No Villager: Disables trading with villagers and wandering traders 💲
- No Armor: Disables the ability of equipping armor 🛡️

#### Goals (can be managed with `/goals`):
- Kill Enderdragon: Beat the enderdragon to complete this goal 💜
- Kill Wither: Beat the mighty boss made of wither skulls and soul sand 💀
- Kill Elder guardian: Defeat the boss of the ocean monument 🏯
- Kill Warden: Beat the boss of the deep dark 🖤

#### Attribute manager (can be accessed with `/attributemanager`):

The attribute manager can be used to change the player's attributes, which includes for example the speed, health and attack damage.
You can find the full list of attributes in the [minecraft wiki](https://minecraft.wiki/w/Attribute).

<img src="https://private-user-images.githubusercontent.com/73256695/382516697-9b1f9d50-0ad5-47ba-971c-2da8e22f57c9.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MzA1OTYzNzEsIm5iZiI6MTczMDU5NjA3MSwicGF0aCI6Ii83MzI1NjY5NS8zODI1MTY2OTctOWIxZjlkNTAtMGFkNS00N2JhLTk3MWMtMmRhOGUyMmY1N2M5LnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDExMDMlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQxMTAzVDAxMDc1MVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTAwMDM0OTlmOWNiMmUxYmFhNjZlMDQ0OWZhYmNlMzM5ODJhMjljZjc3MTI4MzJmODRjNzA4YzE0ODQ4ODcyMjAmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0In0.hrCKab8pV3KScq09A5BfmUQZRwyGfUjul6g6C4Q5wMQ" alt="drawing" width="700"/>

Not only that, but you can also customize to which players these attribute changes apply to with a blacklist or whitelist.


## How to use Verium

1. Download the latest version from [Releases](https://github.com/EnderLuca41/Verium/releases)
2. Go to your plugin folder from your Spigot/Paper server (Plugin does not work on Bukkit servers)
3. Paste the .jar file in and start the server
