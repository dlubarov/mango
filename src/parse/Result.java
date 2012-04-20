package parse;

public final class Result<T> {
    public T value;
    public int rem;

    public Result(T value, int rem) {
        this.value = value;
        this.rem = rem;
    }
}
