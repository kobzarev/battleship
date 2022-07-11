
public enum ShotResult {
    MISS, HIT, DESTROY;

    public boolean isMiss() {
        return this.equals(ShotResult.MISS);
    }
}
