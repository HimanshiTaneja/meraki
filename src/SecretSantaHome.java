import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class SecretSantaHome {

	private Random randomGenerator = new Random();
	public Map<String,String> members = new HashMap<String,String>();
			
	public Map<String, String> secretSantaPairs = new HashMap<>();

	public static void main(String[] args) {
		SecretSantaHome ss = new SecretSantaHome();
		Scanner sc =new Scanner(System.in);
		
		while(true){
			System.out.println("Enter member name: ");
			String name= sc.next();
			System.out.println("Enter member's email id: ");
			String emailId = sc.next();
			ss.members.put(name, emailId);
			System.out.println("Do you wish to add another member? Y or N");
			String addNewMember = sc.next();
			if(addNewMember.equalsIgnoreCase("Y"))
				continue;
			else
				break;
		}
		//sc.close();
		ss.generateSecretSantas();
		EmailService emailService=new EmailService();
		emailService.sendMails(ss.secretSantaPairs,ss.members);

	}

	private void generateSecretSantas() {
		List<String> keys=new ArrayList<String>(members.keySet());
		keys.stream().forEach(
				member -> {
					String value = null;
					while (true) {
						value = getRandomElement(keys);
						if ((value != member)
								&& (!secretSantaPairs.containsValue(value))
								&& (secretSantaPairs.get(value)!= member))
							break;
					}
					secretSantaPairs.put(member, value);
				});
		System.out.println(secretSantaPairs);
	}

	private String getRandomElement(List<String> keys) {
		int index = randomGenerator.nextInt(keys.size());
		String item = keys.get(index);
		return item;
	}
}