import static java.lang.StringTemplate.STR;


void main(String[] args) {
    System.out.print("Hello and welcome!");

    for (int i = 1; i <= 5; i++) {
        System.out.println(STR."i = \{i}");
    }
}
