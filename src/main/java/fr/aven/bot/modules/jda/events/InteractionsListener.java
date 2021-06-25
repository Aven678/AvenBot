package fr.aven.bot.modules.jda.events;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Button;
import org.jetbrains.annotations.NotNull;

public class InteractionsListener extends ListenerAdapter
{
    @Override
    public void onButtonClick(@NotNull ButtonClickEvent event) {
        if (event.getComponentId().equals("salut"))
            event.editMessage("Salut bg, ça va ?").setActionRow(Button.primary("oui", "Oui et toi ?"), Button.secondary("non", "Non et toi ?")).queue();

        if (event.getComponentId().equals("oui")) {
            event.reply("Nice, moi ça va impec !").queue();
        }

        if (event.getComponentId().equals("non")) {
            event.reply("Triste, en tout cas, moi ça va :)").queue();
        }
    }
}
