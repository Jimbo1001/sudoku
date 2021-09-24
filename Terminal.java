class Terminal{
    final static String reset = "\033[H\033[2J";
    final static String redText = "\u001b[31;1m";
    final static String resetText = "\u001b[0m";
    final static String blueText = "\u001b[34;1m";

    final static String[] helpText = new String[]{ "h", "help", "hlp"};
    final static String[] checkText = new String[]{ "c", "check", "ch", "chk" };
    final static String[] clearText = new String[]{ "cl", "clr", "clear" };
    final static String[] exitText = new String[]{ "e", "exit", "ex"};
    final static String[] numberStrings = new String[]{ "0", "1", "2", "3", "4", "5", "6", "7", "8" };
    final static int[] numberInts = new int[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9 };
}