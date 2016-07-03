# rovarspraket-reader
A reader that decorates Readers (such as InputStreamReader) and translates the stream to "[Rövarspråket](https://en.wikipedia.org/wiki/R%C3%B6varspr%C3%A5ket)" as described in the Kalle Blomkvist books. Some effort is made to handle different capitalization cases if RovarspraktedReader decorates a PushbackReader.

##Usage
`Reader rovarReader = new RovarspraketReader(new InputStreamReader(new FileInputStream(TEXT_PATH ), TEXT_ENCODING));`

(See Test.java for a better usage example)
[Test.java](./src/Test.java)
##Example results
Decorating a PushbackReader reading this input:
> "Nåja", sa herr Blomkvist och noppade med överlägsen min av ett grässtrå,
> "för all del, tiotusen kronor är också pengar. Men jag ska säga er, unge man, jag arbetar inte för det snöda guldet.
> Jag har ett enda mål - brottslighetens bekämpande i vårt samhälle. Hercule Poirot, lord Peter Wimsey och undertecknad,
> ja, vi är fortfarande några stycken, som inte tänker tillåta, att kriminaliteten får ta överhand."
>
> MÄSTERDETEKTIVEN BLOMKVIST, 1946

Yields this result:
> "Nonåjoja", sosa hoherorror Boblolomomkokvovisostot ocochoh nonopoppopadode momedod övoverorlolägogsosenon mominon avov etottot > gogroräsossostotrorå,
> "foföror alollol dodelol, totiototusosenon kokrorononoror äror ocockoksoså popenongogaror. Momenon jojagog soskoka sosägoga eror, unongoge momanon, jojagog arorbobetotaror inontote foföror dodetot sosnonödoda goguloldodetot.
> Jojagog hoharor etottot enondoda momålol - bobrorotottotsosloligoghohetotenonsos bobekokämompopanondode i vovårortot sosamomhohälollole. Hoherorcoculole Popoirorotot, lolorordod Popetoteror Wowimomsosey ocochoh unondoderortotecockoknonadod,
> joja, vovi äror foforortotfofaroranondode nonågogrora sostotycockokenon, sosomom inontote totänonkokeror totilollolåtota, atottot kokrorimominonalolitotetotenon fofåror tota övoverorhohanondod."
>
> MOMÄSOSTOTERORDODETOTEKOKTOTIVOVENON BOBLOLOMOMKOKVOVISOSTOT, 1946



