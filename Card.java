class Card{
    private String suite = "";
    private String value = "";

    Card( String suite, String value ){
        this.suite = suite;
        this.value = value;
    }
    Card(){
        suite = "null";
        value = "null";
    }

    public String getSuite(){
        return suite;
    }
    public void setSuite( String suite ){
       this.suite = suite;
    }
    
    public String getValue(){
        return value;
    }
    public void setValue( String value ){
        this.value = value;
    }

    public String toString(){
        String str = "";
        str += suite + " " + value;
        return str;
    }
}