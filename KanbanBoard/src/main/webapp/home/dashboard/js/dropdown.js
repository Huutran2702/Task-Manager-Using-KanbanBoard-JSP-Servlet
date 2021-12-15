var App = {
    accessibleMenu: function () {
        $menu = $(".access-menu");
        $menuItem = $menu.find("> li > a");

        $subMenu = $(".access-submenu");
        $subMenuItem = $subMenu.find("> li > a");
        $submenuLastItem = $subMenu.find("> li:last-child > a");

        $menuItem.bind({
            focus: function () {
                $subMenu.removeClass("is-show");
                if ($(this).next($subMenu)) {
                    $(this).next($subMenu).addClass("is-show");
                }
            },

            blur: function () {
                $subMenu.removeClass("is-show");
            }
        });

        $subMenuItem.bind({
            focus: function () {
                $(this).parent().parent().addClass("is-show");
            }
        });

        $submenuLastItem.bind({
            blur: function () {
                $subMenu.removeClass("is-show");
            }
        });
    },

    cancelAccessibleMenu: function () {
        $(document).click(function () {
            if ($subMenu.hasClass("is-show")) {
                $subMenu.removeClass("is-show");
            }
        });
    }
};

$(function () {
    App.accessibleMenu();
    App.cancelAccessibleMenu();
});