# Verium

## What's Verium
Verium is spigot plugin, that aims to bring different kinds of challenges to minecraft.
Because it is a server plugin, you can also play these challenges with your friends.

## Current supported features:

 - A timer that can be used with `/timer` to measure how well you performed and beat your last record 🚀
 - `/reset` to reset the current world and retry 🔁
 - With `/pause` and `/resume`, you can pause the timer, active challenges, modifiers and goals, set everyone into spectator and pauses the time
   
#### Challenges (can be managed with `/challenges`):
Challenges can fail and easily end you run so be careful.
- No Death: Challenge will fail if a player dies 💀
- Wolf Survive: Every player receives a wolf that must survive at all cost 🦴
- No Fall Damage: Receiving fall damage, even 1HP, will end the challenge 🪶

#### Game modifiers (can be managed with `/modifiers`):
Modifiers do not give you any task and cannot end you run, they only make the game easier or harder.
- No Crafting: Disables the use of crafting tables 🛠️
- No hunger: disable the ability to lose hunger 🍗
- PvP: Enables the possibility to hit and kill other players ⚔️
- UHC: Disables natural regeneration of HP ❤️
- UUHC: Disables every type of regeneration ❤️‍🩹
- No Villager: Disables trading with villagers and wandering traders 💲
- No Armor: Disables the ability of equipping armor 🛡️

#### Goals (can be managed with `/goals`):
Goals give you tasks to complete, if you want to end your run with success, you need to complete the activiated goals.
- Kill Enderdragon: Beat the enderdragon to complete this goal 💜
- Kill Wither: Beat the mighty boss made of wither skulls and soul sand 💀
- Kill Elder guardian: Defeat the boss of the ocean monument 🏯
- Kill Warden: Beat the boss of the deep dark 🖤

#### Attribute manager (can be accessed with `/attributemanager`):

The attribute manager can be used to change the player's attributes, which includes for example the speed, health and attack damage.
You can find the full list of attributes in the [minecraft wiki](https://minecraft.wiki/w/Attribute).

<img src="https://github.com/user-attachments/assets/35ff0261-d0e6-4f73-ae43-6f5f5f1092f1" alt="drawing" width="900"/>

Not only that, but you can also customize to which players these attribute changes apply to with a blacklist or whitelist.

#### Time manager (can be accessed with `/time`):

The time manager allows for changing the time precisecly by inputig in in various formats or based on presets.
It overwrites the vanilla `/time` command, but still implements all the vanilla functions, the only difference is that using the command with no parameters, will open the time manager gui.

<img src="https://github.com/user-attachments/assets/70490d19-5213-44de-bde6-2f6642818961" alt="drawing" width="650"/>

Not only does the GUI make it more easy to change the time, it also allows to freeze it. 🧊

#### Potion effects manager (can be accessed with `/potioneffects`):

The potion effects manager functions similar to the attribute manager, it provides a GUI to give players potion effects with an infinite duration.
Blacklists or whitelists can be used to customize to who the infinite effects apply to.
The GUI does not explain each effect with all details, you can find detailed information about every potion effect in the [minecraft wiki](https://minecraft.wiki/w/Effect).



## How to use Verium

1. Download the latest version from [Releases](https://github.com/EnderLuca41/Verium/releases)
2. Go to your plugin folder from your Spigot/Paper server with a Minecraft version of 1.21.3 or above (Plugin does not work on Bukkit servers)
3. Paste the .jar file into the folder
4. Also install [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997/), if not done already
5. (Recommended) Setup a restart script with this [tutorial](https://gist.github.com/Prof-Bloodstone/6367eb4016eaf9d1646a88772cdbbac5)
