import com.field.middleware.ScriptRunResult

def result = new ScriptRunResult();

result.setRunResult("asdfas")

result.with {
    println runResult;
}

abc();

def abc(){
    println "abc";
    defg();
}


def defg(){
    println "defg";
}

