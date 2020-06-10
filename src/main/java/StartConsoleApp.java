import dao.BillDao;
import dao.UserDao;
import model.Bill;
import model.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class StartConsoleApp {


    public static void main(String[] args) {
        /*
                * Необходимо создать в базе данных несколько сущностей, такие как Пользователь, счет
                У каждого пользователя может быть 0 или более счетов
                Через консоль можно создать пользователя введя его данные (можно выводить запрос по каждому полю отдельно или списком)
                После запуска приложение ожидает команду, например для создание пользователя нужно ввести команду create_user, далее в консоль выведется сообщение какие поля нужно ввести для создания пользователя.
                Команда create_account создает счет. При это нужно указать для какого пользователя, если пользователя нет, то счет не может быть создан
                get_all_users - выводит в консоль всех пользователей из базы
                get_account_info  - выводит информацию о счете для указанного пользователя
                deposit - пополняет указанный счет на указанную сумму
   НЕ РЕАЛИЗОВАННО          ←widtrow  - списывает с указанного счета указанную сумму
   НЕ РЕАЛИЗОВАННО         ←На счете пользователя не может быть сумма меньше 0.0

        *
        * */
        UserDao userDao = new UserDao();
        BillDao billDao = new BillDao();
        final Scanner scanner = new Scanner(System.in);
        System.out.println("~~~ Write a command etc. create_user || if need exit from apps keypress 'off'~~~");
        while (scanner.hasNext()) { // hasNext()
            if(scanner.nextLine().equals("create_user")){
                Scanner sUser = new Scanner(System.in);
                System.out.println("~~~ Now u create new user ~~~");
                System.out.println("~~~ What is his name? ~~~");
                String fname = sUser.nextLine();
                System.out.println("~~~ What is his last name? ~~~");
                String lname = sUser.nextLine();
                System.out.println("~~~ How old is him? ~~~");
                int age = sUser.nextInt();
                User user = new User(0, fname, lname, age);
                userDao.createUser(user);
               // sUser.close(); // под вопрсом
                System.out.println("~~~ finishing create_user ~~~");
            } else if (scanner.nextLine().equals("create_account")){ // не выходило сделать рекурсию
                System.out.println("~~~ Now u r create the Bill for some user ~~~");
                Scanner sBill = new Scanner(System.in);
                System.out.println("~~~ Please write number if user ~~~");
                long user_id = sBill.nextInt();
                if (userDao.ifIssetUser(user_id)){
                    System.out.println("~~~ yes, this user is alive) how many money we can give him ~~~");
                    BigDecimal money = sBill.nextBigDecimal();
                    Bill bill = new Bill(0, user_id, money);
                    billDao.createBill(bill);
                   // sBill.close(); // под вопрсом
                    System.out.println("The bill has been created! What the next?");
                }  else {
                    System.out.println("~~~ Error! We cant find this user? pls try again with commant 'create_account'");
                }

            } else if (scanner.nextLine().equals("get_all_users")){
                System.out.println(userDao.getAllUsers());
            } else if (scanner.nextLine().equals("get_account_info")){
                System.out.println("~~~ Enter the bill_id ~~");
                Scanner sBill = new Scanner(System.in);
                long bill_id = sBill.nextLong();
                System.out.println(billDao.getBillById(bill_id));
            } else if (scanner.nextLine().equals("deposit")){  /// →→→ тут я закончил
                System.out.println("На чей счет перевести?");
                Scanner sBill = new Scanner(System.in);
                long bill_id = sBill.nextLong();
                if (billDao.ifIssetBill(bill_id)){
                    System.out.println("~~~ this bill is isset how many money we can give him?");
                    BigDecimal money =  sBill.nextBigDecimal();
                    billDao.giveMoney(bill_id, money);
                } else {
                    System.out.println("~~~ error, coudnt find this bill ~~~");
                }

                System.out.println(billDao.getBillById(bill_id));
            } else if (scanner.nextLine().equals("off")){ // Приходится 2 раза писать
                System.out.println("~~~ app is over ~~~");
                break;
            }
        }

    }

}
