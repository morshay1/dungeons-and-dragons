package Resources;

public class Resource {
    private int amount;
    private int pool;

    public Resource(int amount, int pool) {
        this.amount = amount;
        this.pool = pool;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int newAmount) {
        this.amount = newAmount;
    }

    public int getPool() {
        return this.pool;
    }

    public void setPool(int newPool) {
        this.pool = newPool;
    }

    public void increaseAmount(int amountToIncrease) {
        this.amount = amount + amountToIncrease;
    }

    public void reduceAmount(int amountToReduce) {
        this.amount = amount - amountToReduce;
    }

    public String toString() {
        return amount + "/" + pool;
    }

}
