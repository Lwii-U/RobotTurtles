package fr.model.board;

import fr.model.graphs.Graph;
import fr.model.graphs.Node;

import java.util.HashMap;
import java.util.LinkedList;

public class Board {

    private HashMap<Integer,String> squaresMap = new HashMap<>();
    //contains the details of what's in each square : key = square's number, value = what's in square

    private Graph boardGraph = new Graph(false);
    //represent the board : for each node, number -> square, name -> type of Piece


    public Board(int nbPlayers) {
        initBoard(nbPlayers);
        test_nbPlayers = nbPlayers;
    }


    private void initBoard(int nbPlayers) {
        //generates a board in a configuration depending on the number of players

        //first generates an empty board with the dimensions: 8 * 7 (doesn't depends on the nb of players)
        initHelperStatic();

        //then completes the board depending on the nb of players
        switch (nbPlayers) {
            case 2:
                initHelper2Players();
                break;
            case 3:
                initHelper3Players();
                break;
            case 4:
                initHelper4Players();
                break;
            default:
                if (nbPlayers < 2) {
                    System.out.println("Error: you must be at least 2 players !");
                }
                else {
                    System.out.println("Error: the maximum number of player is 4 !");
                }
                break;
        }
    }

    private void initHelperStatic() {
        //generates an empty board with the dimensions: 8 * 7 (doesn't depends on the nb of players)

        for (int line = 0; line < 7; ++line) {
            for (int column = 0; column < 6; ++column) {
                squaresMap.put(line*10 + column, Piece.EMPTY.toString());
                boardGraph.addEdge(new Node(line*10 + column, Piece.EMPTY.toString()),
                        new Node(line*10 + column+1, Piece.EMPTY.toString()));
                boardGraph.addEdge(new Node(line*10 + column, Piece.EMPTY.toString()),
                        new Node((line+1)*10 + column, Piece.EMPTY.toString()));
            }
            squaresMap.put(line*10+6, Piece.EMPTY.toString());
        }

        //the 8th line is missing in squaresMap and the nodes of the 8th line aren't linked with each other
        for (int column = 0; column < 6; ++column) {
            squaresMap.put(70 + column, Piece.EMPTY.toString());
            boardGraph.addEdge(new Node(70 + column, Piece.EMPTY.toString()),
                    new Node(70 + column+1, Piece.EMPTY.toString()));
        }
        squaresMap.put(76, Piece.EMPTY.toString());

        //the nodes of the 7th column aren't linked with each other
        for (int line = 0; line < 7; ++line) {
            boardGraph.addEdge(new Node(line*10 + 6, Piece.EMPTY.toString()),
                    new Node((line+1)*10 + 6, Piece.EMPTY.toString()));
        }
    }

    private void initHelper2Players() {
        //completes the board for 2 players

        //adds an 8th column full of StoneWalls
        for (int line = 0; line < 8; ++line) {
            squaresMap.put(line*10 + 7, Piece.STONEWALL.toString());
            boardGraph.addNode(new Node(line*10 + 7, Piece.STONEWALL.toString()));
        }

        //puts the players and the jewel on the board
        squaresMap.replace(1, "Player1");
        boardGraph.get(1).setName(Piece.TURTLE.toString());
        squaresMap.replace(5, "Player2");
        boardGraph.get(5).setName(Piece.TURTLE.toString());

        squaresMap.replace(73, Piece.JEWEL.toString());
        boardGraph.get(73).setName(Piece.JEWEL.toString());
    }

    private void initHelper3Players() {
        //completes the board for 3 players

        //adds an 8th column full of StoneWalls
        for (int line = 0; line < 8; ++line) {
            squaresMap.put(line*10 + 7, Piece.STONEWALL.toString());
            boardGraph.addNode(new Node(line*10 + 7, Piece.STONEWALL.toString()));
        }

        //puts the players and the jewels on the board
        squaresMap.replace(0, "Player1");
        boardGraph.get(0).setName(Piece.TURTLE.toString());
        squaresMap.replace(3, "Player2");
        boardGraph.get(3).setName(Piece.TURTLE.toString());
        squaresMap.replace(6, "Player3");
        boardGraph.get(6).setName(Piece.TURTLE.toString());

        squaresMap.replace(70, Piece.JEWEL.toString());
        boardGraph.get(70).setName(Piece.JEWEL.toString());
        squaresMap.replace(73, Piece.JEWEL.toString());
        boardGraph.get(73).setName(Piece.JEWEL.toString());
        squaresMap.replace(76, Piece.JEWEL.toString());
        boardGraph.get(76).setName(Piece.JEWEL.toString());
    }

    private void initHelper4Players() {
        //completes the board for 4 players

        //adds an 8th column with Empty squares
        for (int line = 0; line < 7; ++line) {
            squaresMap.put(line*10 + 7, Piece.EMPTY.toString());
            boardGraph.addEdge(new Node(line*10 + 7, Piece.EMPTY.toString()),
                    new Node((line+1)*10 + 7, Piece.EMPTY.toString()));
            boardGraph.addEdge(new Node(line*10 + 7, Piece.EMPTY.toString()),
                    new Node(line*10 + 6, Piece.EMPTY.toString()));
        }
        squaresMap.put(77, Piece.EMPTY.toString());
        boardGraph.addEdge(new Node(76, Piece.EMPTY.toString()), new Node(77, Piece.EMPTY.toString()));

        //puts the players and the jewels on the board
        squaresMap.replace(0,"Player1");
        boardGraph.get(0).setName(Piece.TURTLE.toString());
        squaresMap.replace(2, "Player2");
        boardGraph.get(2).setName(Piece.TURTLE.toString());
        squaresMap.replace(5, "Player3");
        boardGraph.get(5).setName(Piece.TURTLE.toString());
        squaresMap.replace(7, "Player4");
        boardGraph.get(7).setName(Piece.TURTLE.toString());

        squaresMap.replace(71, Piece.JEWEL.toString());
        boardGraph.get(71).setName(Piece.JEWEL.toString());
        squaresMap.replace(76, Piece.JEWEL.toString());
        boardGraph.get(76).setName(Piece.JEWEL.toString());
    }

    public HashMap<Integer, String> getSquaresMap() {
        return squaresMap;
    }

    public void updateSquare(int position, String name) {
        if (name.substring(0,3).equals("Pla")) {
            for (int key : squaresMap.keySet()) {
                if (squaresMap.get(key).equals(name)) {
                    squaresMap.put(key, Piece.EMPTY.toString());
                    break;
                }
            }
        }
        squaresMap.put(position, name);
        boardGraph.get(position).setName(Piece.getPieceByName(name).toString());
        if (name.equals(Piece.STONEWALL.toString())) {
            putStoneWallHelper(position);
        }
    }

    private void putStoneWallHelper(int position) {
        //aims at removing the edges between the new wall and the adjacent nodes of the graph

        Node newStoneWall = boardGraph.get(position);
        LinkedList<Node> neighbours = boardGraph.getNeighbours(newStoneWall);
        while (!neighbours.isEmpty()) {
            boardGraph.removeEdge(newStoneWall, neighbours.get(0));
        }
    }

    public boolean pathToJewel(int position) {
        //checks if there is a path to a jewel from the selected position

        LinkedList<Node> pathsAvailable;
        pathsAvailable = boardGraph.breadthFirstSearch(boardGraph.get(position));
        for (Node node : pathsAvailable) {
            if (node.getName().equals(Piece.JEWEL.toString())) {
                return true;
            }
        }
        return false;
    }





    // - - - - - - TEST PART => to DELETE at the end - - - - - - - -

    private int test_nbPlayers;

    public void test_displayBoard() {
        //represents the board thanks to an array of characters which contains the first letter of each Piece

        if (1 < test_nbPlayers && test_nbPlayers < 5) {
            char[][] visualBoard = new char[8][8];

            for (int line = 0; line < 8; ++line) {
                for (int column = 0; column < 8; ++column) {
                    visualBoard[line][column] = squaresMap.get(line * 10 + column).charAt(0);
                }
            }

            for (char[] line : visualBoard) {
                System.out.println(line);
            }
        }
    }
}