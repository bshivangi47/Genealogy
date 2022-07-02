import java.util.*;

/**
 * Author name: Shivangi Bhatt
 * ClassName : HuffmanBinaryTree
 *
 */
public class Main extends Config {
    private static String getEndingString(Scanner userInput) {
        String userArgument = null;

        userArgument = userInput.nextLine();
        userArgument = userArgument.trim();

        // Include a "hack" to provide null and empty strings for testing
        if (userArgument.equalsIgnoreCase("empty")) {
            userArgument = "";
        } else if (userArgument.equalsIgnoreCase("null")) {
            userArgument = null;
        }

        return userArgument;
    }

    public static void main(String[] args) {
        String addPersonCommand = "addPerson";
        String recordAttributesCommand = "recordAttributes";
        String recordReferenceCommand = "recordReference";
        String recordNoteCommand = "recordNote";
        String recordChildCommand = "recordChild";
        String recordPartneringCommand = "recordPartnering";
        String recordDissolutionCommand = "recordDissolution";
        String addMediaFileCommand = "addMediaFile";
        String recordMediaAttributesCommand = "recordMediaAttributes";
        String peopleInMediaCommand = "peopleInMedia";
        String tagMediaCommand = "tagMedia";
        String findPersonCommand = "findPerson";
        String findMediaFileCommand = "findMediaFile";
        String findNameCommand = "findName";
        String findMediaFileNameCommand = "findMediaFileName";
        String findRelationCommand = "findRelation";
        String descendentsCommand = "descendents";
        String ancestoresCommand = "ancestores";
        String notesAndReferencesCommand = "notesAndReferences";
        String findMediaByTagCommand = "findMediaByTag";
        String findMediaByLocationCommand = "findMediaByLocation";
        String findIndividualsMediaCommand = "findIndividualsMedia";
        String findBiologicalFamilyMediaCommand = "findBiologicalFamilyMedia";
        String quitCommand = "quit";

        // Define variables to manage user input

        String userCommand = "";
        String userArgument = "";
        Scanner userInput = new Scanner(System.in);

        // Define the auction system that we will be testing.

        Genealogy genealogy = new Genealogy();
        PeopleManager managePeople = new PeopleManager();
        MediaArchive manageMediaArchive = new MediaArchive();


        // Define variables to catch the return values of the methods

        Integer intOutcome;
        String ignoredString;

        // Let the user know how to use this interface

        System.out.println("Commands available:");
        System.out.println("  " + addPersonCommand);
        System.out.println("  " + recordAttributesCommand);
        System.out.println("  " + recordReferenceCommand);
        System.out.println("  " + recordNoteCommand);
        System.out.println("  " + recordChildCommand);
        System.out.println("  " + recordPartneringCommand);
        System.out.println("  " + recordDissolutionCommand);
        System.out.println("  " + addMediaFileCommand);
        System.out.println("  " + recordMediaAttributesCommand);
        System.out.println("  " + peopleInMediaCommand);
        System.out.println("  " + tagMediaCommand);
        System.out.println("  " + findPersonCommand);
        System.out.println("  " + findMediaFileCommand);
        System.out.println("  " + findNameCommand);
        System.out.println("  " + findMediaFileNameCommand);
        System.out.println("  " + findRelationCommand);
        System.out.println("  " + descendentsCommand);
        System.out.println("  " + ancestoresCommand);
        System.out.println("  " + notesAndReferencesCommand);
        System.out.println("  " + findMediaByTagCommand);
        System.out.println("  " + findMediaByLocationCommand);
        System.out.println("  " + findIndividualsMediaCommand);
        System.out.println("  " + findBiologicalFamilyMediaCommand);
        System.out.println("  " + quitCommand);


        // Process the user input until they provide the command "quit"

        do {
            // Find out what the user wants to do
            userCommand = userInput.next();

                        /* Do what the user asked for.  If condition for each command.  Since each command
                           has a different number of parameters, we do separate handling of each command. */

            if (userCommand.equalsIgnoreCase(addPersonCommand)) {
                System.out.println("Enter the name of the person:");
                String name1 = userInput.next();
                String name2 = getEndingString(userInput);
                String name = name1 + " " + name2;
                PersonIdentity newPerson = managePeople.addPerson(name.trim());
                if (newPerson == null) {
                    System.out.println("null returned for personIdentity");
                } else {
                    System.out.println("newPerson created.");
                }
            } else if (userCommand.equalsIgnoreCase(recordAttributesCommand)) {

                System.out.println("Enter the id of the person:");
                int id = userInput.nextInt();
                PersonIdentity personIdentity = genealogy.findPerson(id);
                Map<String, String> attributes = new HashMap();
                System.out.println("Enter attributes as <key> <value> pair.\n Enter 'end' to stop adding the attributes");
                String attributeString = "";
                do {
                    attributeString = userInput.next();
                    String value = getEndingString(userInput);
                    if (!attributeString.equalsIgnoreCase("end")) {
                        attributes.put(attributeString, value);
                    }
                } while (!attributeString.equalsIgnoreCase("end"));

                System.out.println(managePeople.recordAttributes(personIdentity, attributes));

            } else if (userCommand.equalsIgnoreCase(recordReferenceCommand)) {
                System.out.println("Enter the id of the person:");
                int id = userInput.nextInt();
                PersonIdentity personIdentity = genealogy.findPerson(id);
                System.out.println("Enter the reference of the person:");
                String reference1 = userInput.next();
                String reference2 = getEndingString(userInput);
                String reference = reference1 + " " + reference2;
                System.out.println(managePeople.recordReference(personIdentity, reference));

            } else if (userCommand.equalsIgnoreCase(recordNoteCommand)) {

                System.out.println("Enter the id of the person:");
                int id = userInput.nextInt();
                PersonIdentity personIdentity = genealogy.findPerson(id);
                System.out.println("Enter the note for the person:");

                String note1 = userInput.next();
                String note2 = getEndingString(userInput);
                String note = note1 + " " + note2;
                System.out.println(managePeople.recordNote(personIdentity, note));

            } else if (userCommand.equalsIgnoreCase(recordChildCommand)) {

                System.out.println("Enter the id of the parent:");
                int parentId = userInput.nextInt();
                PersonIdentity parent = genealogy.findPerson(parentId);
                System.out.println("Enter the id of the child:");

                int childId = userInput.nextInt();
                PersonIdentity child = genealogy.findPerson(childId);
                System.out.println(managePeople.recordChild(parent, child));

            } else if (userCommand.equalsIgnoreCase(recordPartneringCommand)) {

                System.out.println("Enter the id of the partner1:");
                int partner1Id = userInput.nextInt();
                PersonIdentity partner1 = genealogy.findPerson(partner1Id);
                System.out.println("Enter the id of the partner2:");

                int partner2Id = userInput.nextInt();
                PersonIdentity partner2 = genealogy.findPerson(partner2Id);
                System.out.println(managePeople.recordPartnering(partner1, partner2));

            } else if (userCommand.equalsIgnoreCase(recordDissolutionCommand)) {

                System.out.println("Enter the id of the partner1:");
                int partner1Id = userInput.nextInt();
                PersonIdentity partner1 = genealogy.findPerson(partner1Id);
                System.out.println("Enter the id of the partner2:");

                int partner2Id = userInput.nextInt();
                PersonIdentity partner2 = genealogy.findPerson(partner2Id);
                System.out.println(managePeople.recordDissolution(partner1, partner2));

            } else if (userCommand.equalsIgnoreCase(addMediaFileCommand)) {

                System.out.println("Enter the name of the file:");
                String name1 = userInput.next();
                String name2 = getEndingString(userInput);
                String name = name1 + " " + name2;

                FileIdentifier newFile = manageMediaArchive.addMediaFile(name.trim());
                if (newFile == null) {
                    System.out.println("null returned for newFile");
                } else {
                    System.out.println("newFile created.");
                }

            } else if (userCommand.equalsIgnoreCase(recordMediaAttributesCommand)) {

                System.out.println("Enter the id of the file:");
                int id = userInput.nextInt();
                FileIdentifier fileIdentifier = genealogy.findMediaFile(id);
                Map<String, String> attributes = new HashMap();
                System.out.println("Enter attributes as <key> <value> pair.\n Enter 'end' to stop adding the attributes");
                String attributeString = "";
                do {
                    attributeString = userInput.next();
                    String value = getEndingString(userInput);
                    if (!attributeString.equalsIgnoreCase("end")) {
                        attributes.put(attributeString, value);
                    }
                } while (!attributeString.equalsIgnoreCase("end"));

                System.out.println(manageMediaArchive.recordMediaAttributes(fileIdentifier, attributes));

            } else if (userCommand.equalsIgnoreCase(peopleInMediaCommand)) {

                System.out.println("Enter the id of the file:");
                int id = userInput.nextInt();
                FileIdentifier fileIdentifier = genealogy.findMediaFile(id);
                List<PersonIdentity> people = new ArrayList<>();
                System.out.println("Enter id of the person\n Enter '-1' to stop adding the attributes");
                int personId = -1;
                do {
                    personId = userInput.nextInt();
                    if (personId > 0) {
                        PersonIdentity person = genealogy.findPerson(personId);

                        people.add(person);
                    }
                } while (personId > 0);

                System.out.println(manageMediaArchive.peopleInMedia(fileIdentifier, people));


            } else if (userCommand.equalsIgnoreCase(tagMediaCommand)) {

                System.out.println("Enter the id of the file:");
                int id = userInput.nextInt();
                FileIdentifier fileIdentifier = genealogy.findMediaFile(id);
                System.out.println("Enter the tag for the file:");
                String tag1 = userInput.next();
                String tag2 = getEndingString(userInput);
                String tag = tag1 + " " + tag2;

                System.out.println(manageMediaArchive.tagMedia(fileIdentifier, tag.trim()));

            } else if (userCommand.equalsIgnoreCase(findPersonCommand)) {

                System.out.println("Enter the name of the person:");
                String name1 = userInput.next();
                String name2 = getEndingString(userInput);
                String name = name1 + " " + name2;
                PersonIdentity personIdentity = genealogy.findPerson(name.trim());
                System.out.println(personIdentity.getId());

            } else if (userCommand.equalsIgnoreCase(findMediaFileCommand)) {

                System.out.println("Enter the name of the media file:");
                String name1 = userInput.next();
                String name2 = getEndingString(userInput);
                String fileName = name1 + " " + name2;
                FileIdentifier fileIdentifier = genealogy.findMediaFile(fileName.trim());
                System.out.println(fileIdentifier.getId());

            } else if (userCommand.equalsIgnoreCase(findNameCommand)) {


                System.out.println("Enter the id of the person:");
                int id = userInput.nextInt();
                PersonIdentity personIdentity = genealogy.findPerson(id);
                System.out.println(genealogy.findName(personIdentity));

            } else if (userCommand.equalsIgnoreCase(findMediaFileNameCommand)) {

                System.out.println("Enter the id of the file:");
                int id = userInput.nextInt();
                FileIdentifier fileIdentifier = genealogy.findMediaFile(id);
                System.out.println(genealogy.findMediaFile(fileIdentifier));

            } else if (userCommand.equalsIgnoreCase(findRelationCommand)) {

                System.out.println("Enter the id of the person1:");
                int person1Id = userInput.nextInt();
                PersonIdentity person1 = genealogy.findPerson(person1Id);
                System.out.println("Enter the id of the person2:");

                int person2Id = userInput.nextInt();
                PersonIdentity person2 = genealogy.findPerson(person2Id);

                BiologicalRelation rel = genealogy.findRelation(person1, person2);
                if (rel != null) {

                    System.out.println("CousinShip: "+rel.getCousinShip());
                    System.out.println("Removal: "+rel.getRemoval());
                } else {

                    System.out.println("null");
                }
            } else if (userCommand.equalsIgnoreCase(descendentsCommand)) {
                System.out.println("Enter the id of the person1:");
                int person1Id = userInput.nextInt();
                PersonIdentity person1 = genealogy.findPerson(person1Id);
                System.out.println("Enter the generations:");

                int generations = userInput.nextInt();

                Set<PersonIdentity> descendents = genealogy.descendents(person1, generations);
                for (PersonIdentity person :
                        descendents) {
                    System.out.println(person.getName());
                }

            } else if (userCommand.equalsIgnoreCase(ancestoresCommand)) {
                System.out.println("Enter the id of the person:");
                int person1Id = userInput.nextInt();
                PersonIdentity person1 = genealogy.findPerson(person1Id);
                System.out.println("Enter the generations:");

                int generations = userInput.nextInt();

                Set<PersonIdentity> ancestors = genealogy.ancestores(person1, generations);
                for (PersonIdentity person :
                        ancestors) {
                    System.out.println(person.getName());
                }
            } else if (userCommand.equalsIgnoreCase(notesAndReferencesCommand)) {
                System.out.println("Enter the id of the person:");
                int person1Id = userInput.nextInt();
                PersonIdentity person1 = genealogy.findPerson(person1Id);

                List<String> notesAndReferences = genealogy.notesAndReferences(person1);
                for (String noteOrReference :
                        notesAndReferences) {
                    System.out.println(noteOrReference);
                }
            } else if (userCommand.equalsIgnoreCase(findMediaByTagCommand)) {
                System.out.println("Enter the tag of the media:");
                String tag = userInput.next();
                System.out.println("Enter the start date");
                String startDate = getEndingString(userInput);
                System.out.println("Enter the end date");
                String endDate = getEndingString(userInput);

                Set<FileIdentifier> medias = genealogy.findMediaByTag(tag, startDate, endDate);
                for (FileIdentifier fileIdentifier :
                        medias) {
                    System.out.println(fileIdentifier.getId() + ": " + fileIdentifier.getFilename());
                }
            } else if (userCommand.equalsIgnoreCase(findMediaByLocationCommand)) {
                System.out.println("Enter the location of the person:");
                String location = userInput.next();
                System.out.println("Enter the start date");
                String startDate = getEndingString(userInput);
                System.out.println("Enter the end date");
                String endDate = getEndingString(userInput);

                Set<FileIdentifier> medias = genealogy.findMediaByLocation(location, startDate, endDate);
                for (FileIdentifier fileIdentifier :
                        medias) {
                    System.out.println(fileIdentifier.getId() + ": " + fileIdentifier.getFilename());
                }
            } else if (userCommand.equalsIgnoreCase(findIndividualsMediaCommand)) {
                Set<PersonIdentity> people = new HashSet<>();
                System.out.println("Enter id of the person\n Enter '-1' to stop adding the attributes");
                int personId = -1;
                do {
                    personId = userInput.nextInt();
                    if (personId > 0) {
                        PersonIdentity person = genealogy.findPerson(personId);

                        people.add(person);
                    }
                } while (personId > 0);
                System.out.println("Enter the start date");
                String startDate = getEndingString(userInput);
                System.out.println("Enter the end date");
                String endDate = getEndingString(userInput);

                List<FileIdentifier> medias = genealogy.findIndividualsMedia(people, startDate, endDate);
                for (FileIdentifier fileIdentifier :
                        medias) {
                    System.out.println(fileIdentifier.getId() + ": " + fileIdentifier.getFilename());
                }
            } else if (userCommand.equalsIgnoreCase(findBiologicalFamilyMediaCommand)) {
                System.out.println("Enter the id of the person:");
                int person1Id = userInput.nextInt();
                PersonIdentity person = genealogy.findPerson(person1Id);
                List<FileIdentifier> medias = genealogy.findBiologicalFamilyMedia(person);
                for (FileIdentifier fileIdentifier :
                        medias) {
                    System.out.println(fileIdentifier.getId() + ": " + fileIdentifier.getFilename());
                }
            } else if (userCommand.equalsIgnoreCase(quitCommand)) {
                System.out.println(userCommand);
            } else {
                System.out.println("Bad command: " + userCommand);
            }
        } while (!userCommand.equalsIgnoreCase("quit"));

        // The user is done so close the stream of user input before ending.

        userInput.close();
    }

}
