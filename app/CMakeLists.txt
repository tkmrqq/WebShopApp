cmake_minimum_required(VERSION 3.4.1)

project(PMSApp CXX)

add_library(cart_lib SHARED src/main/cpp/cart_library.cpp)

add_library(native-lib SHARED src/main/cpp/NativeLib.cpp)

find_library(log-lib log)

target_link_libraries(cart_lib ${log-lib})
target_link_libraries(native-lib cart_lib ${log-lib})
