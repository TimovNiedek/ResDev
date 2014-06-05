# UI Verantwoording
## Algemeen
### Tab Bar
Na enig nadenken heb ik er voor gekozen Hot Meals een tabbed application te maken. Dat wil zeggen, géén action bar (zoals bijvoorbeeld bij SoundCloud), maar een serie tabs boven aan het scherm. Voorlopig zijn er drie tabs:
- Home
- My Recipes
- Search

Een action bar wordt niet gebruikt om van views te switchen, maar om acties uit te voeren die misschien vervolgens naar een andere view gaan. Neem bijvoorbeeld de Search functie in de action bar: Men plaatst hier weliswaar normaliter een 'Search' knop in, maar wanneer deze wordt ingedrukt verschijnt er metéén een on-screen keyboard waar de gebruiker kan zoeken. Dat doen wij niet, omdat we niet alleen op een trefwoord zoeken, maar ook op budget en tijd. Het zou dus verwarrend zijn als de focus meteen op het invoeren van tekst ligt, terwijl de gebruiker misschien op budget wil zoeken.
Een action bar geeft daarnaast geen coherente hierarchie aan. In de tabbed application is het duidelijk dat er drie verschillende schermen zijn, die plaats geven voor de bijbehorende functionaliteit. Bij een action bar moet men de drawer openen om naar andere views te gaan.  
De navigatie in onze app gaat maximaal drie niveaus diep; de gebruiker kan nog een mentaal model bijhouden van waar hij/zij zich in de app bevindt (en kan zo nodig met de hardware back knop terugnavigeren). 
  
> Eerlijk gezegd weet ik niet of we dit nog gaan halen binnen de deadline

Waar we voor moeten zorgen is dat de zoek-tab twee verschillende dingen kan doen: wanneer men er op drukt vanuit een ándere actieve tab verschuift de view naar wat recentelijk bekeken is, maar wanneer men er op drukt vanuit een gevonden recept verschuift de view naar het zoekvenster. (Voorbeeld ter verduidelijking: gebruiker zoekt op spaghetti, krijgt spaghettiresultaten en bekijkt een recept voor spaghetti. Gebruiker navigeert naar de home tab. Wanneer gebruiker nu op search drukt, krijgt hij zijn onlangs bekeken recept voor spaghetti te zien. Maar wanneer de gebruiker tijdens het bekijken van het recept nogmaals op search drukt, krijgt hij het zoekvenster te zien.)
#### Tab Bar Iconography
- Search: Het icoon hiervan is aangegeven in de style guide van Android.
- My Recipes: Het icoon hiervan is ook aangegeven in de style guide van Android. Intuïtief gezien had ik liever voor een ster gekozen, maar de style guide dicteert dat een ster _important_ markeert, en een hart een _favorite_. Aangezien My Recipes haast synoniem met Favorites is, volg ik de style guide en kies ik voor een hart.
- Home: Dit is een lastige keuze. Het gebruik van een huis als icoon voor home/start is sinds Android 3.3 niet meer in zwang. Sterker nog, dit wordt actief afgeraden. Ik wil dus ook geen huis gebruiken. Ik stel voor om, net als SoundCloud, het eigen logo te gebruiken. Omdat deze nog niet ontworpen is, is het huis een tijdelijke plaatsvervanger.
