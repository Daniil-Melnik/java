package ch_7.Exeptions;

public class TestExeption0 extends Exception{

    public TestExeption0(){ // Опрделение конструктора по умолчанию
        super();
    }

    public TestExeption0(String msg) { // Определение конструктора с параметром сообщения
        super(msg);
    }
}
