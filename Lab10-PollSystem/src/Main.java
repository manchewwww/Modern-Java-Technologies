import bg.sofia.uni.fmi.mjt.poll.commands.Command;
import bg.sofia.uni.fmi.mjt.poll.commands.CommandExecutor;
import bg.sofia.uni.fmi.mjt.poll.commands.CreatePollCommand;
import bg.sofia.uni.fmi.mjt.poll.commands.ListPollsCommand;
import bg.sofia.uni.fmi.mjt.poll.commands.SubmitVoteCommand;
import bg.sofia.uni.fmi.mjt.poll.exceptions.InvalidCommandException;
import bg.sofia.uni.fmi.mjt.poll.server.PollServer;
import bg.sofia.uni.fmi.mjt.poll.server.repository.InMemoryPollRepository;
import bg.sofia.uni.fmi.mjt.poll.server.repository.PollRepository;

import java.net.InetSocketAddress;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        PollRepository pollRepository = new InMemoryPollRepository();
        PollServer pollServer = new PollServer(8888, pollRepository);
        pollServer.start();

//        String line1 = "\n";
//        Scanner scanner = new Scanner(System.in);
//        String str = scanner.nextLine();
//        System.out.printf(" dasd%sasd", str);
        String line2 = "";
        String line3 = "list-polls\n";
        String line5 = "list-polls";
        String line6 = "create-poll  What-is-your-favourite-xmas-movie?       Home-Alone asd\n";
//        String line4 = "submit-vote  1 Home-Alone ";
//        String line7 = "list-polls  What-is-your-favourite-xmas-movie?       Home-Alone ";
//        String line8 = "submit-vote  2       Home Alone ";
//        String line9 = "submita-vote  2       Home Alone ";
//        String line10 = "";
//
        CommandExecutor commandExecutor = new CommandExecutor(pollRepository);
//
//        System.out.println(commandExecutor.execute(line1));
        System.out.println(commandExecutor.execute(line6));
        System.out.println(commandExecutor.execute(line3));
//        System.out.println(commandExecutor.execute(line4));
//        System.out.println(commandExecutor.execute(line4));
////        System.out.println(commandExecutor.execute(line5));
////        System.out.println(commandExecutor.execute(line7));
////        System.out.println(commandExecutor.execute(line8));
////        System.out.println(commandExecutor.execute(line10));
////        System.out.println(commandExecutor.execute(line9));

    }

}
