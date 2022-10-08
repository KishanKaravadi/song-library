package song_library.view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import song_library.app.Song;

public class SongLibraryController {

	@FXML
	ListView<Song> listView;
	@FXML Button add, ok, ok2;
	@FXML TextField name, artist, album, year;
	@FXML TextField nameEdit, artistEdit, albumEdit, yearEdit;
	@FXML Text indexEdit;
	
	private Stage primaryStage;
	
	private ObservableList<Song> obsList;
	
	public void start(Stage mainStage) throws FileNotFoundException {
		
		this.primaryStage = mainStage;
		
		//Array of Song objects for convenience. ex:songsArray.contains, songs.getName, etc.
		ArrayList<Song> songsArray = new ArrayList<>();
		
		//Parsing a CSV file through Scanner
		Scanner sc = new Scanner(new File("C:\\Users\\kisha\\eclipse-workspace\\SoftMeth\\src\\song_library\\view\\songs.csv"));
		
		while(sc.hasNextLine()) {
			String[] songParam = sc.nextLine().split(";");
			songsArray.add(new Song(songParam[0], songParam[1], songParam[2], Integer.parseInt(songParam[3])));
			
		}
		
		//Sorts based on toString
		Collections.sort(songsArray, (Song s1, Song s2) ->{
	        return s1.toString().compareToIgnoreCase(s2.toString());
	});
		
		
		obsList = FXCollections.observableArrayList(songsArray); 
		
		//So we can choose what details to show in listView instead of toString
		listView.setCellFactory(param -> new ListCell<Song>() {
            @Override
            protected void updateItem(Song song, boolean empty) {
                super.updateItem(song, empty);

                if (empty || song == null || song.getName() == null) {
                    setText(null);
                } else {
                    setText(song.getName());
                }
            }
        });
		
		listView.setItems(obsList);
		
		
		
		//Select the first item
		//listView.getSelectionModel().select(0);
		
		//Set the listener for the items
		listView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
			try {
				handleShowEditDetails(mainStage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	@FXML
	private void handleAddSongScene(ActionEvent e) throws IOException {
		
			Button addOrOk = (Button)e.getSource();
			
			if(addOrOk == add) {
				Parent addViewParent = FXMLLoader.load(getClass().getResource("/song_library/view/AddView.fxml"));
				
				
				Scene addViewScene = new Scene(addViewParent);
				
				//Gets the stage info
				Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
				
				window.setScene(addViewScene);
				window.show();
			}else if(addOrOk == ok) {
				
				FileWriter fw = new FileWriter(new File("C:\\Users\\kisha\\eclipse-workspace\\SoftMeth\\src\\song_library\\view\\songs.csv"), true);
				BufferedWriter bw = new BufferedWriter(fw);
				
				Song aboutToAdd = new Song(name.getText(), artist.getText(), album.getText(), Integer.parseInt(year.getText()));
				
				bw.write("\n" + aboutToAdd);
				
				bw.close();
				
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/song_library/view/PlaylistView.fxml"));
				
				Parent addViewParent = (Parent)loader.load();
				
				SongLibraryController controller = loader.getController();
				
				
				
				Scene addViewScene = new Scene(addViewParent);
				
				//Gets the stage info
				Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
				
				controller.start(window);
				
				
				window.setScene(addViewScene);
				window.show();
				
			}
			
	}
	
	
	private void handleShowEditDetails(Stage mainStage) throws IOException {
		
		ListView<Song> listEditView = listView;
		ObservableList<Song> obsEditList = obsList;
		
		int index = listEditView.getSelectionModel().getSelectedIndex();
		
		String name = listEditView.getSelectionModel().getSelectedItem().getName();
		String artist = listEditView.getSelectionModel().getSelectedItem().getArtist();
		String album = listEditView.getSelectionModel().getSelectedItem().getAlbum();
		int year = listEditView.getSelectionModel().getSelectedItem().getYear();
		
		
		
		
		
		//Loads Scene
		//Parent editViewParent = FXMLLoader.load(getClass().getResource("/song_library/view/EditView.fxml"));
		
		
		FXMLLoader editLoader = new FXMLLoader(getClass().getResource("/song_library/view/EditView.fxml"));
		
		Parent editViewParent = (Parent)editLoader.load();
		
		SongLibraryController controller = editLoader.getController();
		
		Scene editViewScene = new Scene(editViewParent);
		
		//Gets the stage info
		//Stage window = new Stage();
		
		
		
		mainStage.setScene(editViewScene);
		mainStage.show();
		
		controller.nameEdit.setPromptText(name);
		controller.artistEdit.setPromptText(artist);
		controller.albumEdit.setPromptText(album);
		controller.yearEdit.setPromptText("" + year);
		controller.indexEdit.setText("" + (index + 1));
		
		
		
//		TextInputDialog dialog = new TextInputDialog(name);
//		dialog.setHeaderText("" + index + " Name: " + name + "\n" + "Artist: " + artist + "\n" + "Album: " + album + "\n" + "Year: " + year + "\n" );
//		
//		Optional<String> result = dialog.showAndWait();
//		if(result.isPresent()) {obsList.set(index, new Song(result.get(), artist, album, year));}
		
		
	}
	@FXML
	private void handleUpdatingEdit(ActionEvent e) throws FileNotFoundException {
		
		Scanner sc = new Scanner(new File("C:\\Users\\kisha\\eclipse-workspace\\SoftMeth\\src\\song_library\\view\\songs.csv"));
		
		System.out.print(sc.nextLine());
		
		//obsList.set( (Integer.parseInt(indexEdit.getText())-1) , new Song(nameEdit.getText(), artistEdit.getText(), albumEdit.getText(), Integer.parseInt(yearEdit.getText())));
	
		for(int i=0; i< Integer.parseInt(indexEdit.getText()); i++) {
			sc.nextLine();
		}
		
	
	}
	
	@FXML
	private void handleDelete(ActionEvent e) throws IOException {
		//Changes the CSV; found on internet
		File inputFile = new File("C:\\Users\\kisha\\eclipse-workspace\\SoftMeth\\src\\song_library\\view\\songs.csv");
		File tempFile = new File("myTempFile.txt");

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		String lineToRemove = nameEdit.getPromptText() + ";" + artistEdit.getPromptText() + ";" + albumEdit.getPromptText() + ";" + yearEdit.getPromptText();
		String currentLine;

		while((currentLine = reader.readLine()) != null) {
		    // trim newline when comparing with lineToRemove
		    String trimmedLine = currentLine.trim();
		    if(trimmedLine.equals(lineToRemove)) {
		    	System.out.print(trimmedLine + " " + currentLine.trim());
		    	continue;
		    }
		    writer.write(currentLine + "\n");
		}
		writer.close(); 
		reader.close(); 
		inputFile.delete();
		tempFile.renameTo(inputFile);
		
		//Copied my ok method
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/song_library/view/PlaylistView.fxml"));
		
		Parent addViewParent = (Parent)loader.load();
		
		SongLibraryController controller = loader.getController();
		
		
		
		Scene addViewScene = new Scene(addViewParent);
		
		//Gets the stage info
		Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
		
		controller.start(window);
		
		
		window.setScene(addViewScene);
		window.show();
	}
	
	@FXML
	private void handleCancelButton(ActionEvent e) throws IOException {
		//Copied my ok method
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/song_library/view/PlaylistView.fxml"));
		
		Parent addViewParent = (Parent)loader.load();
		
		SongLibraryController controller = loader.getController();
		
		
		
		Scene addViewScene = new Scene(addViewParent);
		
		//Gets the stage info
		Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
		
		controller.start(window);
		
		
		window.setScene(addViewScene);
		window.show();
	}
	
	
	
	
	
}
