cmake_minimum_required(VERSION 3.16)
project(StudentGradeEvaluator LANGUAGES CXX)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# Automatically look for Homebrew paths on macOS (Apple Silicon & Intel)
if(CMAKE_SYSTEM_NAME STREQUAL "Darwin")
    if(EXISTS "/opt/homebrew")
        list(APPEND CMAKE_PREFIX_PATH "/opt/homebrew")
    elseif(EXISTS "/usr/local")
        list(APPEND CMAKE_PREFIX_PATH "/usr/local")
    endif()
endif()

# Find GLFW package installed via Homebrew
find_package(glfw3 REQUIRED)

# Automatically download Dear ImGui from GitHub
include(FetchContent)
FetchContent_Declare(
    imgui
    GIT_REPOSITORY https://github.com/ocornut/imgui.git
    GIT_TAG        v1.90.4
)
FetchContent_MakeAvailable(imgui)

# Add executable including ImGui core files and backends
add_executable(StudentGradeEvaluator
    main.cpp
    ${imgui_SOURCE_DIR}/imgui.cpp
    ${imgui_SOURCE_DIR}/imgui_demo.cpp
    ${imgui_SOURCE_DIR}/imgui_draw.cpp
    ${imgui_SOURCE_DIR}/imgui_tables.cpp
    ${imgui_SOURCE_DIR}/imgui_widgets.cpp
    ${imgui_SOURCE_DIR}/backends/imgui_impl_glfw.cpp
    ${imgui_SOURCE_DIR}/backends/imgui_impl_opengl3.cpp
)

# Set include directories so compiler finds ImGui headers
target_include_directories(StudentGradeEvaluator PRIVATE
    ${imgui_SOURCE_DIR}
    ${imgui_SOURCE_DIR}/backends
)

# Link libraries and macOS OpenGL framework
target_link_libraries(StudentGradeEvaluator PRIVATE glfw)

if(APPLE)
    target_link_libraries(StudentGradeEvaluator PRIVATE "-framework OpenGL")
endif()
