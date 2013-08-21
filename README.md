SitOfSofa
=========

SitOfSofa

The plugin allows you to sit on the sofas and benches / Chairs.
And yet, sitting comfortably on the couch or bench, you can find different items.
Can you inform all players about what you sit, or have found new items.
All this, as well as, and more, is set in the configuration. :)
Thread on to rubukkit.org
Features:

Multilingual, language changes one command.
The player can is not only sit on the steps, like Similar plug-ins, but also siting on the sofa: the design of a special kind.
The player can find items on sitting.
Automatic rotation at sitting on chair or sofa
The ability to recover health during landing.
Separate configuration settings for benches and sofas.
Configurable timeout before re-finding the items.
You can configurate a private message, on siting on a chair for each player.
Possibility to prohibit players sits down on sofa if front of sofa have a block.
Several variants of a heuristic analysis of the design of benches. hard / medium / low / disabled
Very flexible configuration.
Support [Spout]
That what will in the new releases:

Fix bugs that you find. Performance improvements. The ability to find items with additional features (color, spell, etc.)

Permissions

sofa.sit - Required for all. Allows players to sit.
sofa.reload - Reloading the configuration.
sofa.restoreshealth restore health
sofa.addmessage
sofa.settimeout
sofa.setstartmessage
sofa.setrestoreshealth
sofa.changemessage
sofa.cm
sofa.removemessage
sofa.additemchance
sofa.removeitemchance
sofa.changeitemchance
sofa.changeenable
sofa.setlanguage
Commands

/sofa - view help and all Commands
/sofa setlanguage English - Set english language
/sofa setlanguage Russian - Set russian language
PS: Found a bug? Take the time to describe in detail. So you can quickly to get a working version plugin.

screenshot

screen screen screen screen

ChangeLog

version 1.1.99

[Add] Added a timeout before re-restoration of health. [+] [UsermessageAndChance.yml health.timeoutrestoreshealth: 10000]
[Add] Permissions sofa.restoreshealth
[Delete] Automatic check for updates. Because of the security policy dev.bukkit.org. If I have time and desire I will alter their requirements. [-] [Config.yml updatechecked: true]
[Rename] Changed property on and off health recovery [-] [usermessageAndChance.yml restoreshealth: true] [+] [UsermessageAndChance.yml health.restoreshealth: true]
[Fix] Error in path saving configurations on some operating system
[Fix] Improved stability of the plug-in.
version 1.1.94

[+] The separation of time-outs for the messages and finding objects [usermessageAndChance.yml chanse.timeoutchance: 30000] [usermessageAndChance.yml message.timeoutmessage: 30000]
[+] Check for updates automatically [config.yml updatechecked: true]
[+] The maximum number of different items that the player can find at a time. [usermessageAndChance.yml chanse.maxcountitem 5]
[+] Experimental option: allows you to force check: player fell through during a seat in his chair, and if it fell through puts it back into place. [config.yml antifallthrough: false]
[Fix] With the destruction of stairs you are still sitting.
[Fix] Do not work normally delay obtaining objects, and writing messages
version 1.1.73

[Fix] In some cases, when rising from chair player could fall through the block. If the lower blocks was empty then the player could разбится to death.
TO DO

What do: (Updating on Sundays / Mondays)

Fixed bugs that you finding.
Improved performance [Made in Dev 1.2.03]
The ability to give items with additional features (color, etc.) [Made in Dev 1.2.03-1.2.08]
Ability to find in stack several items. [Made in Dev 1.2.08]
Private bench. Nicky who can sit written on sign. [Made in Dev 1.2.11]
Actions by landing the players are written on the signs. [_ @ private: nikname1; nikname2; nikname3], [_ @ kill], [_ @ give: id; data; count; timeout], [_ @ addxp: xp: timeout], _ @ restorehealth: true / false] [Dev 1.2.11]
Donate

Webmoney

If you liked this plugin. And you want to thank the author for his work as well as for the ongoing maintenance and development projects.

WMZ - Z354634267000
WMR - R278197088605
