package spotitube.services;

import java.util.UUID;

public class UserService
{
    public static String generateUUID()
    {
        return UUID.randomUUID().toString();
    }
}
