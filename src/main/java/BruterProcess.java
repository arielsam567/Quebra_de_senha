import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;


public class BruterProcess {
  private static ArrayList<BruteForceThread> threads = new ArrayList<BruteForceThread>();
  private static int numThreads = 1;
  private static int charListBegin = 0;
  private static int charListEnd = 0;

  public static void main(String[] args) {
    if (args.length != 3) {
      System.out.println("Usage: java BruterProcess <numb of threads> <char list begin> <char list end>");
      System.exit(1);
    }
    numThreads = Integer.parseInt(args[0]);
    charListBegin = Integer.parseInt(args[1]);
    charListEnd = Integer.parseInt(args[2]);

    List<String> chars = BruterSupervisor.CHARS_LIST.subList(charListBegin, charListEnd);
    System.out.println(chars);

    int inGroups = (BruterSupervisor.CHARS_LIST.size() / numThreads) + 1;
    List<List<String>> subSets = Lists.partition(BruterSupervisor.CHARS_LIST, inGroups);

    for (List<String> group : subSets) {
      // Create a storage thread for each brute force thread.
      DisplayPassword store = new DisplayPassword();
      Runtime.getRuntime().addShutdownHook(store); // Show last matched password before exit
      BruteForceThread thread = new BruteForceThread(store, group);
      threads.add(thread);
    }

    // Start all threadds
    for (BruteForceThread thread : threads) {
      thread.start();
    }
  }

  static class DisplayPassword extends Thread {
    private String password;

    public void setPassword(String pass) {
      this.password = pass;
    }

    public void run() {
      if (password != null) {
        System.out.println(BruterSupervisor.SUCCESS_PREFIX + password);
      }
    }
  }

  static class BruteForceThread extends Thread {
    private DisplayPassword store;
    private List<String> chars;

    public BruteForceThread(DisplayPassword store, List<String> chars) {
      this.chars = chars;
      this.store = store;
    }

    public void run() {
      // long tinicial = System.currentTimeMillis();
      String password;
      int charsSize = BruterSupervisor.CHARS_LIST.size();
      // int qtd = 0;
      int charIndex1 = 0;
      int charIndex2 = 0;
      int charIndex3 = 0;
      int charIndex4 = 0;
      int charIndex5 = 0;
      for (String char0 : chars) {
        password = char0;
        checkPassword(password);
        while (charIndex1 < charsSize) {
          password = char0 + BruterSupervisor.CHARS_LIST.get(charIndex1);
          checkPassword(password);
          while (charIndex2 < charsSize) {
            password = char0 + BruterSupervisor.CHARS_LIST.get(charIndex1)
                + BruterSupervisor.CHARS_LIST.get(charIndex2);
            checkPassword(password);
            while (charIndex3 < charsSize) {
              password = char0 + BruterSupervisor.CHARS_LIST.get(charIndex1)
                  + BruterSupervisor.CHARS_LIST.get(charIndex2) + BruterSupervisor.CHARS_LIST.get(charIndex3);
              checkPassword(password);
              while (charIndex4 < charsSize) {
                password = char0
                    + BruterSupervisor.CHARS_LIST.get(charIndex1) + BruterSupervisor.CHARS_LIST.get(charIndex2)
                    + BruterSupervisor.CHARS_LIST.get(charIndex3) + BruterSupervisor.CHARS_LIST.get(charIndex4);
                checkPassword(password);
                while (charIndex5 < charsSize) {
                  password = char0
                      + BruterSupervisor.CHARS_LIST.get(charIndex1) + BruterSupervisor.CHARS_LIST.get(charIndex2)
                      + BruterSupervisor.CHARS_LIST.get(charIndex3) + BruterSupervisor.CHARS_LIST.get(charIndex4)
                      + BruterSupervisor.CHARS_LIST.get(charIndex5);
                  checkPassword(password);
                  charIndex5++;
                }
                ;
                charIndex5 = 0;
                charIndex4++;
              }
              ;
              charIndex4 = 0;
              charIndex3++;
            }
            ;
            charIndex3 = 0;
            charIndex2++;
          }
          ;
          charIndex2 = 0;
          charIndex1++;
        }
        ;
        charIndex1 = 0;
      }
      ;
    }

    private void checkPassword(String password) {
      this.store.setPassword(password);
      String username = "administrador";
      TesteSenha.main(new String[] { username, password });
    }
  }
}
