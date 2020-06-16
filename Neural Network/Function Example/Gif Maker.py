from PIL import Image
import os
 
# Create the frames
frames = []
for i in range(500):
    name = str(i)+'Gen.jpg'
    new_frame = Image.open(name)
    frames.append(new_frame)


    
 
# Save into a GIF file that loops forever
frames[0].save('jpg_to_gif.gif', format='GIF',
               append_images=frames[:],
               save_all=True,
               duration=100, loop=0)
