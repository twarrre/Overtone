attribute vec4 a_position;

uniform vec2 u_Resolution;           // viewport resolution (in pixels)
uniform float u_GlobalTime;           // shader playback time (in seconds)

uniform mat4 u_projTrans;

void main()
{
    gl_Position = u_projTrans * a_position;
}