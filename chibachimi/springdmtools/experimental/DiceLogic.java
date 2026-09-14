package com.chibachimi.springdmtools.experimental;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DiceLogic {

    private Random random;
    private int total;

    public DiceLogic() {
        this.random = new Random();
    }

    public int rollDice(int num, int face, int bonus) {
        total = 0;

        for (int i = 0; i < num; i++) {

            total += this.random.nextInt(1, face + 1);
        }
        return total + bonus;
    }

    public int rollAdv(int bonus) {
        return Math.max(
                rollDice(1, 20, bonus),
                rollDice(1, 20, bonus)
        );
    }

    public int rollDis(int bonus) {
        return Math.min(
                rollDice(1, 20, bonus), rollDice(1, 20, bonus)
        );
    }

    public int flipCoin() {
        return this.random.nextInt(0, 2);
    }

    public void changeRandom() {
        this.random = new Random();
    }

    public int getTotal() {
        return total;
    }
}