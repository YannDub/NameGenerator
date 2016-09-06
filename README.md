# NameGenerator
Random name generator based on the article : http://yahiko.developpez.com/tutoriels/generation-aleatoire-nom/

##How to use

```java
String[] database = {
  "name1", "name2", ..., "nameN"
}
int order = 3;
Markov markov = new Markov(order, database);
```

database -> String array of name, if you want generate firstName, this array will be filled with real firstName
order -> This value get the precision of the generation, 3 is good for firstName
