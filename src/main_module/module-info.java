module main_module{
    requires java.base;
    requires transitive java.desktop;
    exports home to test_module;
}