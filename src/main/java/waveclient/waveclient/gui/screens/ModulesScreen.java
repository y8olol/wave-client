/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.gui.screens;

import waveclient.waveclient.gui.GuiTheme;
import waveclient.waveclient.gui.renderer.GuiRenderer;
import waveclient.waveclient.gui.themes.wave.WaveGuiTheme;
import waveclient.waveclient.gui.tabs.TabScreen;
import waveclient.waveclient.gui.tabs.Tabs;
import waveclient.waveclient.gui.widgets.containers.WContainer;
import waveclient.waveclient.gui.widgets.containers.WSection;
import waveclient.waveclient.gui.widgets.containers.WHorizontalList;
import waveclient.waveclient.gui.widgets.containers.WVerticalList;
import waveclient.waveclient.gui.widgets.containers.WWindow;
import waveclient.waveclient.gui.widgets.input.WTextBox;
import waveclient.waveclient.gui.widgets.pressable.WButton;
import waveclient.waveclient.gui.widgets.pressable.WPressable;
import waveclient.waveclient.systems.config.Config;
import waveclient.waveclient.systems.modules.Category;
import waveclient.waveclient.systems.modules.Module;
import waveclient.waveclient.systems.modules.Modules;
import waveclient.waveclient.utils.misc.NbtUtils;
import waveclient.waveclient.utils.render.color.Color;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.util.MacWindowUtil;
import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.*;

public class ModulesScreen extends TabScreen {
    private WWindow mainWindow;
    private WHorizontalList tabBar;
    private WVerticalList moduleList;
    private WTextBox searchTextBox;
    private WVerticalList searchResults;
    private boolean enabledOnly;

    private final List<TabEntry> tabs = new ArrayList<>();
    private TabEntry selectedTab;

    private static class TabEntry {
        final String name;
        final Category category;
        final boolean favorites;
        final boolean search;
        WModulesTabButton button;

        TabEntry(String name, Category category, boolean favorites, boolean search) {
            this.name = name;
            this.category = category;
            this.favorites = favorites;
            this.search = search;
        }
    }

    public ModulesScreen(GuiTheme theme) {
        super(theme, Tabs.get().getFirst());
    }

    @Override
    public void initWidgets() {
        tabs.clear();

        mainWindow = add(theme.window("Wave Modules")).top().centerX().marginTop(34).widget();
        mainWindow.id = "modules";
        mainWindow.view.hasScrollBar = true;
        mainWindow.view.scrollOnlyWhenMouseOver = false;

        tabBar = mainWindow.add(theme.horizontalList()).expandX().pad(2).widget();
        tabBar.spacing = 2;

        for (Category category : Modules.loopCategories()) {
            List<Module> modules = getCategoryModules(category);
            if (!modules.isEmpty()) tabs.add(new TabEntry(category.name, category, false, false));
        }

        tabs.add(new TabEntry("Favorites", null, true, false));
        tabs.add(new TabEntry("Search", null, false, true));

        for (TabEntry tab : tabs) {
            tab.button = tabBar.add(new WModulesTabButton(tab)).widget();
        }

        mainWindow.add(theme.horizontalSeparator(null)).expandX().padVertical(4);

        moduleList = mainWindow.add(theme.verticalList()).expandX().widget();
        moduleList.spacing = 2;

        if (!tabs.isEmpty()) selectTab(tabs.get(0));

        // Help
        WVerticalList help = add(theme.verticalList()).pad(4).bottom().widget();
        help.add(theme.label("Left click - Toggle module"));
        help.add(theme.label("Right click - Open module settings"));
    }

    // Search

    protected void createSearchW(WContainer w, String text) {
        if (!text.isEmpty()) {
            // Titles
            List<Pair<Module, String>> modules = Modules.get().searchTitles(text);

            if (!modules.isEmpty()) {
                WSection section = w.add(theme.section("Modules")).expandX().widget();
                section.spacing = 0;

                int count = 0;
                for (Pair<Module, String> p : modules) {
                    if (count >= Config.get().moduleSearchCount.get() || count >= modules.size()) break;
                    section.add(theme.module(p.getLeft(), p.getRight())).expandX();
                    count++;
                }
            }

            // Settings
            Set<Module> settings = Modules.get().searchSettingTitles(text);

            if (!settings.isEmpty()) {
                WSection section = w.add(theme.section("Settings")).expandX().widget();
                section.spacing = 0;

                int count = 0;
                for (Module module : settings) {
                    if (count >= Config.get().moduleSearchCount.get() || count >= settings.size()) break;
                    section.add(theme.module(module)).expandX();
                    count++;
                }
            }
        }
    }

    private void createSearchTab() {
        searchTextBox = moduleList.add(theme.textBox("")).minWidth(220).expandX().widget();
        searchTextBox.action = () -> {
            searchResults.clear();
            createSearchW(searchResults, searchTextBox.get());
        };

        searchResults = moduleList.add(theme.verticalList()).expandX().widget();
        searchResults.spacing = 0;
        createSearchW(searchResults, searchTextBox.get());
    }

    @Override
    public boolean keyPressed(KeyInput value) {
        if (locked) return false;

        boolean cntrl = MacWindowUtil.IS_MAC ? value.modifiers() == GLFW_MOD_SUPER : value.modifiers() == GLFW_MOD_CONTROL;

        if (cntrl && value.key() == GLFW_KEY_F) {
            TabEntry searchTab = getSearchTab();
            if (searchTab != null && selectedTab != searchTab) {
                selectTab(searchTab);
            }

            if (searchTextBox != null) {
                searchTextBox.setFocused(true);
                searchTextBox.setCursorMax();
            }

            return true;
        }

        return super.keyPressed(value);
    }

    @Override
    public boolean toClipboard() {
        return NbtUtils.toClipboard(Modules.get());
    }

    @Override
    public boolean fromClipboard() {
        return NbtUtils.fromClipboard(Modules.get());
    }

    @Override
    public void reload() {
    }

    private List<Module> getCategoryModules(Category category) {
        List<Module> modules = new ArrayList<>();
        for (Module module : Modules.get().getGroup(category)) {
            if (!Config.get().hiddenModules.get().contains(module)) modules.add(module);
        }

        modules.sort((a, b) -> String.CASE_INSENSITIVE_ORDER.compare(a.name, b.name));
        return modules;
    }

    private List<Module> getFavoriteModules() {
        List<Module> modules = new ArrayList<>();
        for (Module module : Modules.get().getAll()) {
            if (module.favorite && !Config.get().hiddenModules.get().contains(module)) modules.add(module);
        }

        modules.sort((a, b) -> String.CASE_INSENSITIVE_ORDER.compare(a.name, b.name));
        return modules;
    }

    private TabEntry getSearchTab() {
        for (TabEntry tab : tabs) {
            if (tab.search) return tab;
        }

        return null;
    }

    private void selectTab(TabEntry tab) {
        selectedTab = tab;

        for (TabEntry t : tabs) {
            if (t.button != null) t.button.invalidate();
        }

        moduleList.clear();
        searchTextBox = null;
        searchResults = null;

        if (tab.search) {
            createSearchTab();
            return;
        }

        WHorizontalList filters = moduleList.add(theme.horizontalList()).expandX().widget();
        filters.spacing = 2;

        filters.add(theme.label("Filter:")).widget();

        WButton all = filters.add(theme.button("All")).widget();
        all.action = () -> {
            enabledOnly = false;
            selectTab(selectedTab);
        };

        WButton enabled = filters.add(theme.button("Enabled")).widget();
        enabled.action = () -> {
            enabledOnly = true;
            selectTab(selectedTab);
        };

        moduleList.add(theme.horizontalSeparator()).expandX().padBottom(2);

        List<Module> modules = tab.favorites ? getFavoriteModules() : getCategoryModules(tab.category);
        if (enabledOnly) modules = modules.stream().filter(Module::isActive).toList();
        for (Module module : modules) {
            moduleList.add(theme.module(module)).expandX();
        }

        if (modules.isEmpty()) {
            moduleList.add(theme.label(tab.favorites ? "No favorites yet." : "No modules in this tab.")).expandX();
        }
    }

    private static Color mix(Color a, Color b, double t) {
        if (t <= 0) return new Color(a);
        if (t >= 1) return new Color(b);

        return new Color(
            (int) Math.round(a.r + (b.r - a.r) * t),
            (int) Math.round(a.g + (b.g - a.g) * t),
            (int) Math.round(a.b + (b.b - a.b) * t),
            (int) Math.round(a.a + (b.a - a.a) * t)
        );
    }

    private class WModulesTabButton extends WPressable {
        private final TabEntry tab;
        private double textWidth;
        private double hoverProgress;
        private double activeProgress;

        private WModulesTabButton(TabEntry tab) {
            this.tab = tab;
        }

        @Override
        public double pad() {
            return theme.scale(5);
        }

        @Override
        protected void onCalculateSize() {
            double pad = pad();
            textWidth = theme.textWidth(tab.name);

            width = pad + textWidth + pad;
            height = pad + theme.textHeight() + pad;
        }

        @Override
        protected void onPressed(int button) {
            selectTab(tab);
        }

        @Override
        protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
            boolean active = selectedTab == tab;
            WaveGuiTheme waveTheme = (WaveGuiTheme) theme;

            hoverProgress += delta * 10 * (mouseOver ? 1 : -1);
            hoverProgress = Math.max(0, Math.min(1, hoverProgress));

            activeProgress += delta * 10 * (active ? 1 : -1);
            activeProgress = Math.max(0, Math.min(1, activeProgress));

            Color idle = new Color(waveTheme.backgroundColor.get(false, false));
            Color hovered = new Color(waveTheme.backgroundColor.get(false, true));
            Color accent = new Color(waveTheme.accentColor.get());

            Color body = mix(idle, hovered, hoverProgress * 0.8);
            body = mix(body, accent, activeProgress * 0.35);
            renderer.quad(this, body);

            if (activeProgress > 0) {
                double underlineWidth = Math.max(theme.scale(12), width * activeProgress);
                renderer.quad(x + width / 2 - underlineWidth / 2, y + height - theme.scale(2), underlineWidth, theme.scale(2), accent);
            }

            Color idleText = new Color(waveTheme.textSecondaryColor.get());
            Color activeText = new Color(waveTheme.titleTextColor.get());
            Color textColor = mix(idleText, activeText, Math.max(hoverProgress * 0.65, activeProgress));

            renderer.text(tab.name, x + width / 2 - textWidth / 2, y + pad(), textColor, false);
        }
    }
}
