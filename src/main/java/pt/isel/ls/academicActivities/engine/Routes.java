package pt.isel.ls.academicActivities.engine;

import pt.isel.ls.academicActivities.commands.academicClass.*;
import pt.isel.ls.academicActivities.commands.course.*;
import pt.isel.ls.academicActivities.commands.functional.*;
import pt.isel.ls.academicActivities.commands.programme.*;
import pt.isel.ls.academicActivities.commands.student.GetStudentsOfClass;
import pt.isel.ls.academicActivities.commands.student.GetStudentsOfClassOrderedByNumber;
import pt.isel.ls.academicActivities.commands.student.PostStudentToClass;
import pt.isel.ls.academicActivities.commands.student.RemoveStudentFromClass;
import pt.isel.ls.academicActivities.commands.teacher.*;
import pt.isel.ls.academicActivities.commands.user.*;
import pt.isel.ls.academicActivities.commands.Command;
import pt.isel.ls.academicActivities.request.Request;
import pt.isel.ls.academicActivities.utils.Pair;

import java.util.*;

public class Routes {
    public static List<Pair<String, Command>> cmdList;
    private CommandNode root;

    private Routes() {
        root = new CommandNode();
        fillCmdTree();
    }

    private static void fillCmdList() {
        cmdList = new ArrayList<>();
        cmdList.add(new Pair<>("GET/courses/{acr}/classes", new GetClassesOfCourse()));
        cmdList.add(new Pair<>("GET/courses/{acr}/classes/{sem}", new GetClassesOfCourseOnSemester()));
        cmdList.add(new Pair<>("GET/courses/{acr}/classes/{sem}/{num}", new GetSpecificClassOfCourse()));
        cmdList.add(new Pair<>("GET/courses", new GetAllCourses()));
        cmdList.add(new Pair<>("GET/courses/{acr}", new GetSpecificCourse()));
        cmdList.add(new Pair<>("GET/programmes", new GetAllProgrammes()));
        cmdList.add(new Pair<>("GET/programmes/{pid}/courses", new GetProgrammeCourses()));
        cmdList.add(new Pair<>("GET/programmes/{pid}", new GetSpecificProgramme()));
        cmdList.add(new Pair<>("GET/teachers/{num}/classes", new GetClassesTaughtByTeacher()));
        cmdList.add(new Pair<>("GET/courses/{acr}/classes/{sem}/{num}/teachers", new GetTeachersOfClass()));
        cmdList.add(new Pair<>("GET/students", new GetAllStudents()));
        cmdList.add(new Pair<>("GET/teachers", new GetAllTeachers()));
        cmdList.add(new Pair<>("GET/students/{num}", new GetSpecificStudent()));
        cmdList.add(new Pair<>("GET/teachers/{num}", new GetSpecificTeacher()));
        cmdList.add(new Pair<>("GET/users", new GetUsers()));
        cmdList.add(new Pair<>("GET/courses/{acr}/classes/{sem}/{num}/students", new GetStudentsOfClass()));
        cmdList.add(new Pair<>("GET/courses/{acr}/classes/{sem}/{num}/students/sorted", new GetStudentsOfClassOrderedByNumber()));
        cmdList.add(new Pair<>("POST/programmes/{pid}/courses", new PostCourseIntoProgramme()));
        cmdList.add(new Pair<>("POST/programmes", new PostProgramme()));
        cmdList.add(new Pair<>("POST/courses", new PostCourse()));
        cmdList.add(new Pair<>("POST/courses/{acr}/classes/{sem}/{num}/teachers", new PostTeacherToClass()));
        cmdList.add(new Pair<>("POST/courses/{acr}/classes", new PostClass()));
        cmdList.add(new Pair<>("POST/students", new PostStudent()));
        cmdList.add(new Pair<>("POST/teachers", new PostTeacher()));
        cmdList.add(new Pair<>("POST/courses/{acr}/classes/{sem}/{num}/students", new PostStudentToClass()));
        cmdList.add(new Pair<>("PUT/teachers/{num}", new UpdateTeacher()));
        cmdList.add(new Pair<>("PUT/students/{num}", new UpdateStudent()));
        cmdList.add(new Pair<>("DELETE/courses/{acr}/classes/{sem}/{num}/students/{numStu}", new RemoveStudentFromClass()));
        cmdList.add(new Pair<>("OPTION/", new Option()));
        cmdList.add(new Pair<>("EXIT/", new Exit()));
        cmdList.add(new Pair<>("LISTEN/", new Listen()));

    }

    private void fillCmdTree() {
        for (Pair<String, Command> pair : cmdList) {
            Command cmd = pair.getValue();
            String[] path = pair.getKey().split("/");
            CommandNode treePointerNode = root;
            CommandNode commandNodeAux;
            for (int i = 0; i < path.length; i++) {
                commandNodeAux = treePointerNode.getChild(path[i]);
                if (commandNodeAux == null) {
                    commandNodeAux = new CommandNode();
                    treePointerNode.addChild(path[i], commandNodeAux);
                }
                treePointerNode = commandNodeAux;
            }
            treePointerNode.setInstance(cmd);
        }
    }

    public Optional<RouteInfo> getRoute(Request request) {
        RouteInfo routeInfo = new RouteInfo();
        String[] fullPath = getFullPath(request.getMethod(), request.getPath());
        CommandNode commandNode = root;
        for (String aPath : fullPath) {
            commandNode = commandNode.getChild(aPath);
            if (commandNode == null)
                return Optional.empty();
            if (commandNode.getVarName() != null) {
                String key = commandNode.getVarName().replace("{", "").replace("}", "");
                routeInfo.addToVarPathList(new Pair<>(key, aPath));
            }
        }
        routeInfo.setCommand(commandNode.getInstance());
        return routeInfo.getCommand() == null ? Optional.empty() : Optional.of(routeInfo);
    }

    private String[] getFullPath(String method, String[] partialPath) {
        String[] fullPath;
        if (partialPath.length == 0) {
            fullPath = new String[]{method};
        } else {
            fullPath = new String[partialPath.length];
            fullPath[0] = method;
            System.arraycopy(partialPath, 1, fullPath, 1, fullPath.length - 1);
        }
        return fullPath;
    }

    public static Routes create() {
        fillCmdList();
        return new Routes();
    }

    class CommandNode {
        private CommandNode varChild;
        private String varName;
        private Command instance;
        private Map<String, CommandNode> children;

        public CommandNode(Command instance) {
            this.instance = instance;
            children = new HashMap<>();
        }

        public CommandNode() {
            children = new HashMap<>();
        }

        public String getVarName() {
            return varName;
        }

        public void setVarName(String varName) {
            this.varName = varName;
        }

        public Command getInstance() {
            return instance;
        }

        public void setInstance(Command instance) {
            this.instance = instance;
        }

        public void addChild(String partialPath, CommandNode child) {
            if (partialPath.startsWith("{") && partialPath.endsWith("}")) {
                child.setVarName(partialPath);
                varChild = child;
            } else
                children.put(partialPath, child);
        }

        public CommandNode getChild(String path) {
            CommandNode res = children.get(path);
            return res == null ? varChild : res;
        }
    }
}