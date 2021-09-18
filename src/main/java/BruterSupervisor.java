import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.collect.Lists;

public class BruterSupervisor {
  public static Boolean successOutput = false;
  public static String password;
  public static final String SUCCESS_PREFIX = "The password is password: ";
  public static final String SUCCESS_OUTPUT = "Acesso concedido!";
  public static final ArrayList<String> CHARS_LIST = Lists.newArrayList("0", "1", "2", "3", "4", "5", "6", "7", "8",
      "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
      "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
      "T", "U", "V", "W", "X", "Y", "Z");

  private static ArrayList<Process> processes = new ArrayList<Process>();

  public static void main(String[] args) {
    if (args.length != 2) {
      System.out.println("Usage: java Main <number of processes> <number of threads>");
      System.exit(1);
    }
    int max = CHARS_LIST.size();
    int numProcesses = Integer.parseInt(args[0]);
    int numThreads = Integer.parseInt(args[1]);
    if (numProcesses < 1 || numProcesses > max) {
      System.out.println("Number of processes must be between 1 and " + max);
      System.exit(1);
    }
    if (numThreads < 1 || numThreads > max) {
      System.out.println("Number of threads must be between 1 and " + max);
      System.exit(1);
    }
    System.out.println("Initializing..");
    System.out.println("--> Number of processes: " + numProcesses);
    System.out.println("--> Number of threads: " + numThreads);
    ;

    Runtime r = Runtime.getRuntime();
    System.out.println("Total of Cores: " + r.availableProcessors());
    System.out.println("Total memory: " + r.totalMemory());
    System.out.println("Free memory: " + r.freeMemory());
    System.out.println("Memory occupied: " + (r.totalMemory() - r.freeMemory()));

    if (r.availableProcessors() < numProcesses) {
      System.out.println("WARNING! Number of processes is greater than number of processors");
      System.exit(1);
    }

    int inGroups = (CHARS_LIST.size() / numProcesses) + 1;
    List<List<String>> subSets = Lists.partition(CHARS_LIST, inGroups);

    int i = 0;
    for (List<String> group : subSets) {
      i += 1;
      String fromChar = group.get(0);
      String toChar = group.get(group.size() - 1);
      System.out.println("Starting process: " + i + " => " + fromChar + " to " + toChar);

      try {
        startBruterProcess(numThreads, CHARS_LIST.indexOf(fromChar), CHARS_LIST.indexOf(toChar));
      } catch (IOException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  // public static void buildCharsList() {
  // for (int i = 0; i <= 9; i++) {
  // CHARS_LIST.add(Integer.toString(i));
  // }
  // for(char c : "qwertyuiopasdfghjklzxcvbnm".toCharArray()) {
  // CHARS_LIST.add(Character.toString(c));
  // CHARS_LIST.add(Character.toString(c).toUpperCase());
  // }
  // }

  public static void startBruterProcess(int numThreads, int fromChar, int toChar)
      throws IOException, InterruptedException {
    // String fullClassName = BruterProcess.class.getName();
    // String pathToClassFiles = new File("./target/classes").getPath();
    // String pathSeparator = File.pathSeparator; // ":" on Linux, ";" on Windows
    // String pathToLib = new File("./src/main/resources/TesteSenha.jar").getPath();
    // String[] commands = {"java", "-cp", pathToLib + pathSeparator +
    // pathToClassFiles, fullClassName, "myArg"};

    String classpath = System.getProperty("java.class.path");
    String className = BruterProcess.class.getName();

    List<String> command = new ArrayList<>();
    command.add(getJavaExecutablePath());
    command.add("-cp");
    command.add(classpath);
    command.add(className);
    command.add(Integer.toString(numThreads));
    command.add(Integer.toString(fromChar));
    command.add(Integer.toString(toChar));

    ProcessBuilder builder = new ProcessBuilder(command);
    // Process process = builder.inheritIO().start();
    Process process = builder.start();
    processes.add(process);
    inheritIO(process, process.getInputStream(), System.out);
    inheritIO(process, process.getErrorStream(), System.err);
  }

  private static void inheritIO(Process process, final InputStream src, final PrintStream dest) {
    new Thread(new Runnable() {
      public void run() {
        Scanner sc = new Scanner(src);
        while (sc.hasNextLine()) {
          String text = sc.nextLine();
          if (text.equals(BruterSupervisor.SUCCESS_OUTPUT)) {
            BruterSupervisor.successOutput = true;
          }
          if (text.startsWith(BruterSupervisor.SUCCESS_PREFIX)) {
            BruterSupervisor.password = text;
          }
          if (BruterSupervisor.successOutput == true && BruterSupervisor.password != null) {
            System.out.println("Password found!" + BruterSupervisor.password);
            for (Process p : BruterSupervisor.processes) {
              if (p != process) {
                p.destroy();
              }
            }
            System.exit(0);
          } else {
            dest.println(text);
          }

        }
      }
    }).start();
  }

  private static String getJavaExecutablePath() {
    String javaHome = System.getProperty("java.home");
    File f = new File(javaHome);
    f = new File(f, "bin");
    f = new File(f, "java");
    return f.getAbsolutePath();
  }
}