package services;

public class IdService
{
    /**
     * Checks if an array of integers are all valid.
     *
     * @param ids The list of ID's
     * @return Is valid.
     */
    public boolean isValid(int... ids)
    {
        for (int id : ids) {
            if (id <= 0) {
                return false;
            }
        }

        return true;
    }
}
