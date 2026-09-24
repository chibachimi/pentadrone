package com.chibachimi.springdmtools.logic;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DiceLogic {

    private Random random;
    private int total;

    public DiceLogic() {
        this.random = new Random();
    }

    public String rollDice(int num, int face, int bonus) {
        total = 0;

        for (int i = 0; i < num; i++) {

            int r = this.random.nextInt(1, face + 1);

            System.out.println(r);

            if (face == 20) {
                if (r == 20) return "Natural 20";
                if (r == 1) return "Natural 1";
            }
            total += r;
            r = 0;
//            total += this.random.nextInt(1, face + 1);
//            if (total == 20 || total == 1) {
//                return total;
//            }
        }
        System.out.println(total + bonus);
        return String.valueOf(total + bonus);
    }

    private int rollTwenty() {
        return this.random.nextInt(1, 21);
    }

    public String rollAdv(int bonus) {
        var roll1 = rollTwenty();
        var roll2 = rollTwenty();

        System.out.println(roll1);
        System.out.println(roll2);

        if (roll1 == 20 || roll2 == 20) {
            return "Natural 20";
        }

        roll1 += bonus;
        roll2 += bonus;

        return String.valueOf(
                Math.max(
                        roll1,
                        roll2
                )
        );
    }

    public String rollDis(int bonus) {
        var roll1 = this.random.nextInt(1, 21);
        var roll2 = this.random.nextInt(1, 21);

        System.out.println(roll1);
        System.out.println(roll2);

        if (roll1 == 1 || roll2 == 1) {
            System.out.println("nat 1");
            return "Natural 1";
        }

        roll1 += bonus;
        roll2 += bonus;

        return String.valueOf(
                Math.min(
                        roll1,
                        roll2
                )
        );
    }

    public String flipCoin() {
        return String.valueOf(
                this.random.nextInt(0, 2)
        );
    }

    public void changeRandom() {
        this.random = new Random();
    }

    public int getTotal() {
        return total;
    }
}