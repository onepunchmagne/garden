# Vegetables Garden

This is a little codebase accompanying my live-coding conference "Un démarrage TDD accéléré" _("An accelerated start of
TDD")_ performed at Lyon Craft on the 4th of April.

The goal is not to boast a state-of-the-art result at all 🙈 You'll find this repo ugly in some ways. This is a one-shot
try, but you should be proud and confident about your tries. Because TDD is all about that: building things little step
by little step. And getting the amazing possibility to change your design later.

The goal is to emphasize on the **simplicity to actually start Test-Driven Development**. But also to go through some
steps that really changed my way of understanding and practicing TDD.

I wrote [this article](https://blog.ippon.fr/2023/01/18/mon-apprentissage-du-tdd/) on the exact same subject here if
you'd like more "written stuff". Well, there's quite a lot of written stuff here, I can't stop typiiiiiing.

## Trying to reformulate the things you need to understand about TDD

1. Accept to start somewhere. Anywhere is fine. Just start. Go. Fly. With practice you'll eventually start becoming more
   confident about starting somewhere specifically, such as defining the domain first, or your services first. It does
   not matter for the moment.
2. Anytime you get a compilation error, use your magical IDE context menu to get a suggestion. And use that keyboard
   shortcut especially often, like always: Alt+Enter for me. Your IDE will actually resolve most stuff. Well, you may
   need to help it sometimes, you still have a job don't worry. (bis) Read especially
   carefully [the three laws of TDD by Uncle Bob](http://butunclebob.com/ArticleS.UncleBob.TheThreeRulesOfTdd),
   it is short, but requires a lot of introspection and patience to get all the amazing content it has to offer.
3. Baby-steps approach is so powerful it will actually build you a full-grown mountain by assembling grains of sand.
   Each step is a business new understanding: "a crop's variety is mandatory". Well no big deal uh? Yeah exactly! No big
   deal. There should not be big deals, only baby-steps. Keep going. Be confident.
4. TDD confidence is all about the consistent security net it brings. Confident because your 100% code coverage is well
   deserved: you did not look for it especially, but well it's here, just accept it with open arms. Consistent because
   regressions this net will detect will be legit, accurate and really helpful.
5. So refactor as much as you want. This is your biggest gift. Refactor your tests, refactor your code, until you're
   satisfied with both of them. Your tests are here and they secure any form of regression, they are your living
   documentation.
6. Well I said "as much as you want"? Ok perhaps "almost as you want". You may read
   about [transformation priority premise](http://blog.cleancoder.com/uncle-bob/2013/05/27/TheTransformationPriorityPremise.html)
   and the kind of traps you may find yourself in by transformating the behavior accidentally when trying to transform
   only your code structure.
7. Do you struggle refactoring because your tests are too tightly coupled to your implementation? This is so
   frustrating damn! But the solution resides in the different types of tests. I myself love the testing pyramid not for
   the "try-to-estimate-how-many-unit-tests-versus-e2e" I will get, but the many values that I can read through it: the
   quick feedback of unit tests, great reach of component tests. Mix them for your own purpose: a component test (see
   Gherkin `garden.feature` implemented as a Cucumber test) will test component (here a Spring backend) as a blackbox.
   It does not care about the inside, it only cares about getting a global behavior. This will make you totally free of
   creating an hexagonal architecture or anything else the way you'd prefer. It's a form
   of [social testing](https://martinfowler.com/bliki/UnitTest.html). But social testing can also be done at unit test
   level for DDD aggregates easily, or anything you'd like. You choose. At the end of the day, the key is to be able to
   use any kind of test for the advantages it will bring.
8. TDD will force you to become fluent with testing tools such as Gherkin, Cucumber, JUnit, SpringExtension... TDD will
   never require a masterclass-level at knowing DDD patterns, hexagonal architecture and great stuff like that. But: TDD
   will become the empowerment of learning all this great stuff. You just learnt "Rich Model"? The synergy with TDD will
   be awesome. You want to learn hexagonal architecture? Try it for real, TDD will back you up on the final behavior of
   your application. You previously misunderstood a part about hexagonal architecture and just understood it better?
   Nice you can refactor it with ease. Think about TDD this way: in a video game, TDD will be your permanent boost "✨
   experience X10 ✨" _(Epic Shiny Explosive Potion of Knowledge)_

## What kind of tests for what portion of code?

Personally, and this will evolve, but this gives me pleasure at the moment of writing so if you are totally lost you may
try it until you reach your own understanding:

1. I mostly start with a component test for defining the global behavior of my feature. This is Outside-In TDD, double
   loop. At most I'll want a very few tens of component tests for documenting the whole behavior of the application.
   When I say "behavior" here I'm pointing out the overall one: "you can move an arm", I don't care about "but no more
   than it stays stuck to your shoulder", nor do I care about "what should be an ok-size for your arm". I'm not testing
   limits nor validation here, the combinations would become explosive, and component tests are way too slow for that. I
   would be testing limits if they would be an overall behavior: "when moving an arm out of your shoulder, then a
   third arm pops out of nowhere".
   On that note, be wary when writing your Gherkin. You should write only simple to understand business sentences. Never
   fall in the "I'll add this technical information" or this will become messy.
2. I write unit tests for my domain (hexagonal architecture) following Non-Anemic Domain Model approach (= Rich Model),
   and for my mappers in all ways (Json <> Rest <> Domain <> JPA Entity). This will get me some hundreds to thousands of
   unit tests, but note again that the debate is not the number. The debate is the usefulness of having tens of
   immediate-feedback unit tests telling me "this Rich Model entity works great".
3. I need integration tests for checking my JPA repositories with Liquibase schemas updates. You know all those string
   table names and column names that cannot be detected at compilation. Or those weird but handy "
   findByCropVarietyAndSeasonality(...)" queries you're actually trying to guess :D
4. I don't especially need end-to-end, but 2 or 3 may be of help. As a last resort they _may_ catch some problems. I
   define an end-to-end test failure as "a victory for the production, but a failure for the development team". They
   need to be deployed, so out-of-reach from a development immediate feedback. It's hard to wait behind your screen to
   get the CI feedback, so mostly you went to do something else, and a failure will mean context switching again. But
   don't get me wrong: they may be useful. As long as you don't rely on them too much.

## Ubiquitous language used

* nouns: crop > seeding > plant > fruit
* actions: sow > water > harvest

## Associated resources

* [The three laws of TDD - Uncle Bob](http://butunclebob.com/ArticleS.UncleBob.TheThreeRulesOfTdd)
* [Non-Anemic Domain Model - Martin Fowler](https://martinfowler.com/bliki/AnemicDomainModel.html)
* [80 ou 90% de couverture pour un nouveau projet ? - Colin Damon](https://blog.ippon.fr/2019/07/22/80-ou-90-de-couverture-de-tests/)
* [Type-Driven Development - Anthony Rey](https://blog.ippon.fr/2021/02/08/type-driven-development/)

## Enjoy ❤️

We have such a beautiful work when we learn to banish any form of "luck" and embrace practices bringing non-accidental
quality. Hope you'll find great pleasure, trust and confidence in your daily, bringing business value to life 😌

## Compile and start in quick

```bash
npm install
```

```bash
./mvnw
```
