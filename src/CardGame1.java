import java.util.Arrays;
import java.util.Scanner;



// 카드는 카드무늬, 카드 숫자, toString을 통해서 카드를 확인할 수 있어야 해.
// 완료 플레이어는 고유한 닉네임, 돈(초기값 10000원 설정), 승, 패 , static 메서드 이름짓기() 필요 + Scanner로 받기, Card[] 5card = 
// 완료 딜러는 랭크체크해주고(void ㄴㄴ, String으로 반환해야지 score를 줄수 있음.) , 그 랭크체크한것을 토대로 점수를 줘 (int형으로 반환 rankcheck메서드가 FOURCARD면 5를줘,FLUSH면 4,STRAIGHT면 3, THREECARD면 2 PAIR는 1 NORANK면 0 )  
// 완료 카드덱은 카드배열을 가지고 있어야하고, 카드덱을 초기화해줄 때 최대한 for문 줄여. 카드배열에 카드 넣는거 잊지말고
// 완료 + 카드덱을 섞어야헤 (void shuffle()), 카드를 뽑아줘(Card[] pickedCard()) -매개변수는 ?deck에서 0~4,5~9,10~14,15~19을뽑아야하는데, for문 사용)

// 가장 높은 win을 가진 애를 차례로 순서정리. main()에서 처리.

class CardDeck{ // 카드덱은 
    private Card[] cArr; // 카드배열을 가지고 있고.

    CardDeck() {
        // 문제점 1 (배열초과)
        //Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: 13
        // 배열이 13이 넘어가버림. 0부터 시작해서 12까지

        cArr = new Card[52]; // 카드 덱을 52개 설정
        for(int i = 0; i < cArr.length;i++) { // 생성해서 만들어주기
            cArr[i]= new Card((i%13)+1,i/13); // Card c = new Card();
        }
    }
    void shuffle() {
        for(int i = 0; i<cArr.length;i++) {
            int n = (int)(Math.random()*52);
            Card tmp = cArr[i];
            cArr[i] = cArr[n];
            cArr[n] = tmp;
        }
    }
    public Card[] pick(int index) {

        Card[] pickedCards = new Card[5];
        for(int i =0; i < 5; i++) { //5번 반복
            pickedCards[i] = cArr[index+i];
        }
        return pickedCards;
    }

}


//딜러는 랭크체크해주고(void ㄴㄴ, String으로 반환해야지 score를 줄수 있음.) , 그 랭크체크한것을 토대로 점수를 줘 (int형으로 반환 rankcheck메서드가 FOURCARD면 5를줘,FLUSH면 4,STRAIGHT면 3, THREECARD면 2 PAIR는 1 NORANK면 0 )  

class Dealer{
    String rankCheck(Card[] cards) {


        int[] cntArr= new int[13];

        String a ="NORANK";

        for(int i= 0; i<cards.length;i++) {

            cntArr[cards[i].num-1]=cntArr[cards[i].num-1]+1;
            for(int j =0; j<cntArr.length; j++) {
                if(cards[0].kind==cards[1].kind && cards[0].kind==cards[2].kind && cards[0].kind==cards[3].kind && cards[0].kind==cards[4].kind) {
                    a = "FLUSH";
                    break;
                } else if(cntArr[j]==4) {a= "FOURCARD";
                    break;
                } else if (cntArr[j]==3) {a= "THREECARD";
                    break;
                } else if(cntArr[j]==2) {
                    a = "PAIR";
                    break;
                } else if(cntArr[j]==1&&j<9) { // j가 9를넘으면 배열초과예외발생
                    // Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: 13
                    if(cntArr[j+1]==1 && cntArr[j+2]==1 && cntArr[j+3]==1 && cntArr[j+3]==1 &&cntArr[j+4]==1) {
                        a="STRAIGHT";
                    }
                }
            } // 완성된 for문
        }
        return a;
    } // ends of method rankCheck

    public int setScore(String rank) { // rankCheck에서 나온 String을 받아서 int형으로 반환.
        if(rank == "FOURCARD") return 5;
        else if (rank == "FLUSH") return 4;
        else if(rank == "STRAIGHT") return 3;
        else if(rank == "THREECARD") return 2;
        else if(rank == "PAIR") return 1;
        return 0;

    }

}
class Player{
    String name;
    int money;
    int win;
    int lose;
    int score;
    Card[] cards5;
    Player(){}
    Player(String name, int money, int win, int lose) {
        this.name = name;
        this.money = money;
        this.win = win;
        this.lose = lose;
    }
    void getCard(Card[] cards5) {
        this.cards5 = cards5;
    }

    void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Player " + name + "게임머니: "+money +", 승: " + win+", 패: "+lose;
    }
    static Player createNewPlayer(String name) {
        while(name.length()>20) {
            if((name.length()>20)) {
                System.out.println("다시 이름을 정해주세요. >");
                Scanner sc = new Scanner(System.in);
                String rename = sc.nextLine();
                name = rename;
            }
        }
        return new Player(name, 10000, 0, 0);
    }

}
public class CardGame1 {

    public static void main(String[] args) {


        Scanner sc = new Scanner(System.in);
        System.out.println("이름을 입력해주세요. >");
        String nickname= sc.nextLine();
        // 플레이어를 만들고 ,
        Player p = Player.createNewPlayer(nickname);
        Player p2 = Player.createNewPlayer("Lusy");
        Player p3 = Player.createNewPlayer("Dev");
        Player p4 = Player.createNewPlayer("asda1234");

        Dealer d = new Dealer();
        CardDeck deck = new CardDeck();


        // 게임플레이
        System.out.println("게임을 횟수를 정하세요. >");
        int gameCount = sc.nextInt(); // 100이 들어와야겠지?
        for(int i = 0; i< gameCount; i++) {
            deck.shuffle(); // 덱을 섞고
            //카드를 뽑고
            Card[] pickedCards1 = deck.pick(0);
            Card[] pickedCards2 = deck.pick(5);
            Card[] pickedCards3 = deck.pick(10);
            Card[] pickedCards4 = deck.pick(15);

            // 플레이어 이름 , 뽑은카드, (딜러가 세팅한)점수 보여줘야 함
            info(p,pickedCards1, d);
            info(p2,pickedCards2, d);
            info(p3,pickedCards3, d);
            info(p4,pickedCards4, d);

            // 만약 제일 높은 rank를 가지고 있으면, 그 Player에게 win++, 나머지는 lose++; 이거 딜러가 판단해야겠지?

            finalCheck(new Player[] {p,p2,p3,p4});
            System.out.println(p.toString());
            System.out.println(p2.toString());
            System.out.println(p3.toString());
            System.out.println(p4.toString());
            // 순위테이블
            totalTable(new Player[] {p,p2,p3,p4});
        }

    } // ends of main

    // 만약 제일 높은 rank를 가지고 있으면, 그 Player에게 win++과 돈 += 100원, 나머지는 lose++;
    static void finalCheck(Player[] players) {
        int[] scores = new int[players.length];
        for (int i = 0; i < players.length; i++) {
            scores[i] = players[i].score;
        }

        // 스코어 정렬
        Arrays.sort(scores);

        // 가장 높은 스코어를 찾음
        int maxScore = scores[scores.length - 1];

        // 가장 높은 스코어를 가진 플레이어의 win을 증가시키고, 나머지는 lose를 증가시킴
        for (int i = 0; i < players.length; i++) {
            if (players[i].score == maxScore) {// 비교
                players[i].win++;
                players[i].money +=100;
            } else {
                players[i].lose++;
            }
        }

    }
    // 카드정보와 순위
    static void info(Player p, Card[] cards5, Dealer d) {
        System.out.println(p.name+"의 카드");
        p.getCard(cards5);
        for (Card card : cards5) {
            System.out.println(card);
        }
        String rank= d.rankCheck(cards5);
        System.out.println(rank);
        p.setScore(d.setScore(rank));
        System.out.println("=====================");
    }

    // 순위테이블
    static void totalTable(Player[] p) {
        System.out.println("최종결과 순위");
        Player[] players = p;
        for (int i = 0; i < players.length; i++ ) {
            for(int j =0; j<players.length-1; j++) {

                if (players[j].win < players[j+1].win) { // 첫번쨰 두번쨰중 두번째가 크면, 자리바꾸기.
                    Player tmp = players[j+1];
                    players[j+1] = players[j];
                    players[j] = tmp;
                }
            }
        }

        // 위의 정리된 배열을 순서대로 출력.
        for (int i = 0; i < players.length; i++) {
            int rank = i + 1;
            System.out.println("순위  " + rank + " 이름: " + players[i].name + ", 승: " + players[i].win + ", 패: " + players[i].lose);
        }
    }
} // ends of class CardGame1



class Card {
    int num; // 숫자

    int kind; // 무늬

    public String toString() {
        if(kind == 0) {
            return "Card " + num +" H";
        }else if(kind == 1) {
            return "Card " + num +" S";
        }else if(kind == 2) {
            return "Card " + num +" C";
        }else if(kind == 3){
            return "Card " + num +" D";
        }else {
            return "";
        }
    }
    Card() {}
    Card(int num, int kind){
        this.num = num;
        this.kind = kind;
    }
} 