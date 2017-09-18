package pt.isel.ls.academicActivities.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.BeforeClass;
import org.junit.Test;

import pt.isel.ls.academicActivities.commands.programme.GetAllProgrammes;
import pt.isel.ls.academicActivities.request.Request;
import pt.isel.ls.academicActivities.exceptions.CommandException;

public class RoutesTests {
    private static Routes routes;

    @BeforeClass
    public static void init() throws Exception {
        routes = Routes.create();
    }

    @Test
    public void testGetInstanceOfCommand() throws CommandException {
        Request request = new Request("GET","/programmes");
        try {
            assertEquals(GetAllProgrammes.class, routes.getRoute(request).get().getCommand().getClass());
        } catch (Exception e) {
            throw new CommandException();
        }
    }

    @Test
    public void testGetInstanceThatDoesntExist() throws CommandException {
        Request request = new Request("POST","/users");
        assertFalse(routes.getRoute(request).isPresent());
    }
}
