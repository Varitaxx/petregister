package eu.asgardschmiede.petregister;

import eu.asgardschmiede.petregister.model.Owner;
import eu.asgardschmiede.petregister.model.Pet;
import eu.asgardschmiede.petregister.model.Status;
import eu.asgardschmiede.petregister.model.Type;
import eu.asgardschmiede.petregister.repository.OwnerRepository;
import eu.asgardschmiede.petregister.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootApplication
@RequiredArgsConstructor // Baut einen Konstruktor für finale Felder
@Transactional
public class PetregisterApplication implements CommandLineRunner {

    // Werden über eine Constructor-Injection zugewiesen, daher kein @Autowired nötig
    private final PetRepository petRepository;

    private final OwnerRepository ownerRepository;

    private final PasswordEncoder passwordEncoder;

	/* // wird durch Lombok gebaut (@RequiredArgsConstructor)
	public PetregisterApplication(PetRepository petRepository, OwnerRepository ownerRepository, PasswordEncoder passwordEncoder) {
		this.petRepository = petRepository;
		this.ownerRepository = ownerRepository;
		this.passwordEncoder = passwordEncoder;
	}
	*/

    public static void main(String[] args) {
        SpringApplication.run(PetregisterApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

		/*
		var owner = Owner.builder()
								.firstname("Peter")
								.lastname("Parker")
								.email("p.parker@shield.org")
								.password(passwordEncoder.encode("geheim#123"))
								.build();
		ownerRepository.save(owner);
		var pet = Pet.builder()
								.birthDate(LocalDate.of(2020, 1, 1))
								.chipId("1234567890")
								.name("Bello")
								.type(Type.DOG)
								.status(Status.OK)
								.race("Dackel")
								.owner(owner)
								.build();
		petRepository.save(pet);
		*/

        // owner.addPet(pet); // statt owner.getPets().add(pet)
        // ownerRepository.save(owner);
    }
}
