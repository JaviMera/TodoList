package todo.javier.mera.todolist.exceptions;

/**
 * Created by Javier on 11/28/2016.
 */

public class EmptyTodoTitleException extends Exception {

    @Override
    public String getMessage() {

        return "Can't add a list without a title.";
    }
}
