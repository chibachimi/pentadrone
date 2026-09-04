package com.chibachimi.springdmtools.ui.views;

import com.vaadin.flow.component.markdown.Markdown;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


@Route("/help")
public class HelpView extends VerticalLayout {

    public HelpView() {
        setAlignItems(Alignment.CENTER);

        Markdown markdown = new Markdown(markdownText());
        add(markdown);
    }

    private String markdownText() {
        return """
                # Welcome to Pentadrone!
                Named for the Modron species in the Forgotten Realms setting.
                
                # What This Does
                Pentadrone is a simple tool to help Dungeon Masters organize their games, players, their characters,
                and to make it easier to keep track of initiative during combat.
                
                # How It Works
                This program *uses your computer's storage* to store all of the information you input, while
                using Spring and Vaadin as a backend and frontend respectively to display your information in the web browser.
                This was chosen because other similar tools for Dungeons and Dragons, such as Roll20 or DndBeyond, are also
                in browsers.
                
                # Games
                This will display a grid of games taken from the games/ folder.
                
                To create a new game, press the "Create A New Game" button. This will add a game to the list.
                
                To edit a game, new or not, select it in the grid then press the "Edit Selected Game" button.
                This will bring up a submenu where you can change the name, add or remove players, and add or remove characters.
                Save your game pressing the "Save" button, cancel any changes using the "Cacnel" button.
                To delete the game, press the "Delete Game" button, which will then pop up a confirmation.
                *This is ireversable*.
                
                Games are stored as json files in your games/ folder.
                
                # Initiative
                This will display a line of buttons and a grid. To populate the grid, select a button with one of your games on it.
                This will place all of the characters from your game onto the grid. By default, everyone has an initiative of zero.
                Double click on a character to change their initiative value. Once done, press the arrows above the initiative column twice.
                This will auto sort everyone listed.
                
                To add a new character (or monster), click the "Add A New Character" button.
                To remove a character, click on a character on the grid, then press the the "Remove Selected Character" button.
                
                # Close Process
                Pressing this will terminate the background process and server that make the program run. You will need to do this if you want
                to restart the program or refresh it.
                
                If you exit the tab without pressing this button, it will continue to run in the background.
                
                # Important Reminder
                Pentradrone stores all files on your computer under the "dmtools" folder.
                On Windows, this will be in the AppData folder.
                On Mac, in the Applications Library folder.
                On Linux, it will be a hidden dot file.
                Please backup these files as you see fit.
                Pentadrone does not collect information about you. The source code (that does not include Spring or Vaadin)
                is located at https://github.com/chibachimi/pentadrone.
                This software does not have accounts. This software does not collect emails. I do not want to know anything about you.
                
                Thank you for using and supporting Pentadrone! Further updates will happen, and you can see the progress at the
                GitHub link listed above.
                """;
    }
}
