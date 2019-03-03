package baseball;

import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.In;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BaseballElimination {

    private class Team {
        int id;
        String name;
        int wins;
        int losses;
        int remaining;

        public Team(int id, String name, int wins, int losses, int remaining) {
            this.id = id;
            this.name = name;
            this.wins = wins;
            this.losses = losses;
            this.remaining = remaining;
        }

        String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Team{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", wins=" + wins +
                    ", losses=" + losses +
                    ", remaining=" + remaining +
                    '}';
        }
    }

    private Map<String, Team> teams;
    private int[][] against;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In("input/baseball/" + filename);
        int n = in.readInt();
        teams = new HashMap<>();
        against = new int[n][n];
        for (int i = 0; i < n; i++) {
            String teamName = in.readString();
            teams.put(teamName, new Team(i, teamName, in.readInt(), in.readInt(), in.readInt()));
            for (int j = 0; j < n; j++) {
                against[i][j] = in.readInt();
            }
        }
        System.out.println(teams);
        System.out.println(Arrays.deepToString(against));
    }

    // number of teams
    public int numberOfTeams() {
        return teams.size();
    }

    // all teams
    public Iterable<String> teams() {
        return teams.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        return teams.get(team).wins;
    }

    // number of losses for given team
    public int losses(String team) {
        return teams.get(team).losses;
    }

    // number of remaining games for given team
    public int remaining(String team) {
        return teams.get(team).remaining;
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        int id1 = teams.get(team1).id;
        int id2 = teams.get(team2).id;
        return against[id1][id2];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        if (teams.size() == 1) {
            // deals with cases like teams1.txt
            return false;
        }
        Team target = teams.get(team);
        // Trivial elimination.
        for (Team temp : teams.values()) {
            if (!temp.getName().equals(team) && temp.wins >= target.wins + target.remaining) {
                return true;
            }
        }

        // Nontrivial elimination: use maxflow
        return false;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        return null;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        System.out.println(division.numberOfTeams());
        System.out.println(division.teams());
        System.out.println(division.wins("Hufflepuff"));
        System.out.println(division.losses("Gryffindor"));
        System.out.println(division.remaining("Ravenclaw"));
        System.out.println(division.against("Hufflepuff", "Slytherin"));
//        for (String team : division.teams()) {
//            if (division.isEliminated(team)) {
//                StdOut.print(team + " is eliminated by the subset R = { ");
//                for (String t : division.certificateOfElimination(team)) {
//                    StdOut.print(t + " ");
//                }
//                StdOut.println("}");
//            } else {
//                StdOut.println(team + " is not eliminated");
//            }
//        }
    }
}
