import java.util.Scanner;

public class Rpg_before_class {
    static Scanner in = new Scanner(System.in);
    static int hero_level, hero_power, hero_hp, hero_defense, hero_mp, hero_experience, hero_money, hero_max_hp, hero_speed;
    static int monster_hp, monster_defense, monster_power, monster_mp, monster_level, monster_experience, monster_money, monster_speed;
    static String hero_name, monster_name;
    static int choice;

    public static void main(String[] args) {
        System.out.print("영웅의 이름을 입력하세요. : ");
        hero_name = in.next();
        System.out.println("이름이 입력되었습니다.");
        System.out.println("게임에 입장합니다.");
        reset_hero();
        show_profile();
        while (true) {
            System.out.println();
            System.out.println("1. 사냥터");
            System.out.println("2. 포션 상점");
            System.out.println("3. 종료");
            System.out.println();
            System.out.print("입장할 장소를 선택하세요 : ");
            choice = in.nextInt();
            switch (choice) {
                case 1:
                    goto_hunt();
                    break;
                case 2:
                    goto_potion_shop();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("올바른 선택지를 입력해주세요.");
                    break;
            }
        }
    }
    // 전투 시스템

    public static int hero_attack() {
        return hero_level * 10 + hero_power * 30;
    }

    public static int monster_attack() {
        return monster_level * 10 + monster_power * 30;
    }

    public static boolean hero_attacked(int sum) {
        if (sum >= hero_defense) {
            hero_hp -= sum - hero_defense;
            System.out.println(monster_name + "의 타격!");
            System.out.println(hero_name + "은 " + (sum - hero_defense) + "만큼의 데미지를 입었습니다.");
            if (hero_hp < 0) {
                System.out.println(hero_name + "가 죽었습니다.");
                reset_hero();
                return true;
            }
        }
        System.out.println(monster_name + "의 공격은 효과가 없었습니다.");
        return false;
    }

    public static boolean monster_attacked(int sum) {
        if (sum >= monster_defense) {
            monster_hp -= sum - monster_defense;
            System.out.println(hero_name + "의 타격!");
            System.out.println(monster_name + "은 " + (sum - monster_defense) + "만큼의 데미지를 입었습니다.");
            if (monster_hp < 0) {
                System.out.println(monster_name + "가 죽었습니다.");
                System.out.println("경험치 +" + monster_experience + " 획득");
                System.out.println("골드 +" + monster_money + " 획득");
                hero_experience += monster_experience;
                hero_money += monster_money;
                return true;
            }
        }
        System.out.println(hero_name + "의 공격은 효과가 없었습니다.");
        return false;
    }

    public static void goto_hunt() {
        System.out.println();
        System.out.println("사냥터에 입장했습니다.");
        System.out.println("1. 너구리");
        System.out.println("2. 살쾡이");
        System.out.print("전투할 상대를 입력하세요. : ");
        choice = in.nextInt();
        switch (choice) {
            case 1:
                reset_monster("너구리");
                break;
            case 2:
                reset_monster("살쾡이");
                break;
            default:
                System.out.println("올바른 선택지가 아니므로 너구리를 선택합니다.");
                reset_monster("너구리");
                break;
        }
        System.out.println();
        System.out.println("전투에 돌입합니다.");

        int heroTurnTimer = 200 - hero_speed;
        int monsterTurnTimer = 200 - monster_speed;
        int Timer = 0;

        while (true) {
            Timer++;
            if (heroTurnTimer % Timer == 0 && monster_attacked(hero_attack())) {
                check_level_up();
                break;
            }
            if (monsterTurnTimer % Timer == 0 && hero_attacked(monster_attack())) {
                reset_hero();
                show_profile();
                break;
            }
        }
    }

    // 몬스터 구성

    public static void reset_monster(String monster) {
        if (monster.equals("너구리")) {
            monster_name = monster;
            monster_hp = 100;
            monster_mp = 0;
            monster_level = 1;
            monster_power = 20;
            monster_defense = 5;
            monster_money = 10;
            monster_experience = 10;
            monster_speed = 80;
        } else if (monster.equals("살쾡이")) {
            monster_name = monster;
            monster_hp = 2000;
            monster_mp = 0;
            monster_level = 5;
            monster_power = 100;
            monster_defense = 20;
            monster_money = 30;
            monster_experience = 50;
            monster_speed = 120;
        }
    }

    //레벨업 시스템
    public static void check_level_up() {
        if (hero_experience >= hero_level * 80) {
            hero_level++;
            System.out.println(hero_name + "의 레벨이 상승했습니다 레벨 : " + hero_level);
            System.out.printf("골드 +%d 획득\n", hero_level * 50);
            hero_money += hero_level * 50;
            System.out.println("총 골드 : " + hero_money);
            System.out.println("체력이 최대치까지 회복되었습니다.");
            hero_hp = hero_max_hp;
        }
    }

    //포션 상점
    public static void goto_potion_shop() {
        System.out.println("포션 상점에 입장했습니다.");
        while (true) {
            boolean exit = false;
            System.out.println("1. 힘 증강 포션 (30원)");
            System.out.println("2. 방어력 증강 포션 (30원)");
            System.out.println("3. 경험치 증가 포션 (100원)");
            System.out.println("4. HP 증강 포션 (10원)");
            System.out.println("5. MP 증강 포션 (10원)");
            System.out.println("6. 나가기");
            System.out.print("원하시는 물건을 입력하세요: ");
            choice = in.nextInt();
            switch (choice) {
                case 1:
                    if (hero_money < 30) {
                        System.out.println("골드가 부족합니다.");
                        break;
                    }
                    hero_power += 3;
                    hero_money -= 30;
                    System.out.println("구입이 완료됐습니다.");
                    System.out.println("힘이 3만큼 상승했습니다.");
                    show_profile();
                    break;
                case 2:
                    if (hero_money < 30) {
                        System.out.println("골드가 부족합니다.");
                        break;
                    }
                    hero_defense += 3;
                    hero_money -= 30;
                    System.out.println("구입이 완료됐습니다.");
                    System.out.println("방어력이 3만큼 상승했습니다.");
                    show_profile();
                    break;
                case 3:
                    if (hero_money < 100) {
                        System.out.println("골드가 부족합니다.");
                        break;
                    }
                    hero_experience += 50;
                    hero_money -= 100;
                    System.out.println("구입이 완료됐습니다.");
                    System.out.println("경험치가 50만큼 상승했습니다.");
                    check_level_up();
                    show_profile();
                    break;
                case 4:
                    if (hero_money < 10) {
                        System.out.println("골드가 부족합니다.");
                        break;
                    }
                    hero_max_hp += 50;
                    hero_hp += 50;
                    hero_money -= 10;
                    System.out.println("구입이 완료됐습니다.");
                    System.out.println("최대 체력이 50만큼 상승했습니다.");
                    show_profile();
                    break;
                case 5:
                    if (hero_money < 10) {
                        System.out.println("골드가 부족합니다.");
                        break;
                    }
                    System.out.println("구입이 완료됐습니다.");
                    hero_mp += 50;
                    hero_money -= 10;
                    System.out.println("MP가 50만큼 상승했습니다.");
                    show_profile();
                    break;
                case 6:
                    if (hero_money < 100) {
                        System.out.println("골드가 부족합니다.");
                        break;
                    }
                    if (hero_speed + 10 > 150) {
                        System.out.println("속도는 150보다 더 높아질 수 없습니다.");
                        break;
                    }
                    hero_speed += 10;
                    hero_money -= 100;
                    System.out.println("구입이 완료됐습니다.");
                    System.out.println("속도가 10만큼 상승했습니다.");
                    show_profile();
                    break;
                case 7:
                    exit = true;
                    break;
                default:
                    System.out.println("올바른 선택지를 입력해주세요.");
            }
            if (exit) {
                exit = false;
                break;
            }
        }
    }

    //유틸리티

    public static void show_profile() {
        System.out.println("********************");
        System.out.println();
        System.out.println("현재 영웅의 이름 : " + hero_name);
        System.out.println("현재 " + hero_name + "의 레벨 : " + hero_level);
        System.out.println("현재 " + hero_name + "의 힘 : " + hero_power);
        System.out.println("현재 " + hero_name + "의 방어력 : " + hero_defense);
        System.out.println("현재 " + hero_name + "의 체력 : " + hero_hp);
        System.out.println("현재 " + hero_name + "의 속도 : " + hero_speed);
        System.out.println("현재 " + hero_name + "의 경험치 : " + hero_experience);
        System.out.println("현재 " + hero_name + "의 골드 : " + hero_money);
        System.out.println();
        System.out.println("********************");
    }

    public static void reset_hero() {
        hero_level = 1;
        hero_power = 15;
        hero_defense = 25;
        hero_hp = 80;
        hero_experience = 0;
        hero_money = 0;
        hero_speed = 100;
    }
}
