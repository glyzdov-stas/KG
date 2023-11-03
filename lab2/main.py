import os
import shutil
import tkinter as tk

import cv2
import numpy as np

cd = os.getcwd()


def clear_folder(folder_path):
    file_list = os.listdir(folder_path)
    for file_name in file_list:
        file_path = os.path.join(folder_path, file_name)
        shutil.rmtree(file_path)


def adaptive_thresholding(image, constant=2 / 3, block_size=11):
    processed = cv2.adaptiveThreshold(image, 255, cv2.ADAPTIVE_THRESH_MEAN_C, cv2.THRESH_BINARY, block_size,
                                      1 / constant)

    return processed


def global_thresholding_by_histogramm(image):
    hist = cv2.calcHist([image], [0], None, [256], [0, 256])
    total_pixels = image.shape[0] * image.shape[1]
    cumulative_sum = np.cumsum(hist)

    threshold = 0
    max_variance = 0

    for t in range(256):
        w0 = cumulative_sum[t] / total_pixels
        w1 = (total_pixels - cumulative_sum[t]) / total_pixels

        mu0 = np.sum(np.multiply(hist[:t + 1], np.arange(t + 1))) / (cumulative_sum[t] + 1e-8)
        mu1 = np.sum(np.multiply(hist[t + 1:], np.arange(t + 1, 256))) / (
                cumulative_sum[255] - cumulative_sum[t] + 1e-8)

        variance = w0 * w1 * (mu0 - mu1) ** 2

        if variance > max_variance:
            max_variance = variance
            threshold = t

    processed = cv2.threshold(image, threshold, 255, cv2.THRESH_BINARY)[1]

    return processed


def global_thresholding_otsu(image):
    _, thresholded = cv2.threshold(image, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)
    return thresholded


def laplacian_filter(image):
    kernel = np.array([[-1, -1, -1],
                       [-1, 9, -1],
                       [-1, -1, -1]], dtype=np.float32)

    filtered_image = cv2.filter2D(image, -1, kernel)

    return filtered_image


def LoG_filter(image):
    kernel = np.array(
        [[0, 0, -1, 0, 0],
         [0, -1, -2, -1, 0],
         [-1, -2, 16, -2, -1],
         [0, -1, -2, -1, 0],
         [0, 0, -1, 0, 0]],
        dtype=np.float32)

    filtered_image = cv2.filter2D(image, -1, kernel)

    return filtered_image


def LoGinv_filter(image):
    kernel = np.array(
        [[0, 0, -1, 0, 0],
         [0, -1, -2, -1, 0],
         [-1, -2, 16, -2, -1],
         [0, -1, -2, -1, 0],
         [0, 0, -1, 0, 0]],
        dtype=np.float32)

    filtered_image = cv2.filter2D(image, -1, kernel)
    filtered_image = np.invert(filtered_image)

    return filtered_image


def select_input_directory():
    input_directory = cd + '\\Input'
    input_entry.delete(0, tk.END)
    input_entry.insert(0, input_directory)


def select_output_directory():
    output_directory = cd + '\\Output'
    output_entry.delete(0, tk.END)
    output_entry.insert(0, output_directory)


def process_images():
    input_directory = input_entry.get().replace('/', '\\')
    output_directory = output_entry.get().replace('/', '\\')
    clear_folder(output_directory)

    methods = [laplacian_filter,
               LoG_filter,
               LoGinv_filter,
               global_thresholding_by_histogramm,
               global_thresholding_otsu,
               adaptive_thresholding]

    for method in methods:
        method_output_directory = os.path.join(output_directory, method.__name__)
        os.makedirs(method_output_directory, exist_ok=True)

        image_files = os.listdir(input_directory)
        for image_file in image_files:
            print(image_file)
            if image_file.endswith('.png') or image_file.endswith('.jpg') or image_file.endswith(
                    '.bmp') or image_file.endswith('.BMP'):
                image_path = os.path.join(input_directory, image_file)
                print(image_path)
                image = cv2.imread(image_path, cv2.IMREAD_GRAYSCALE)

                processed_image = method(image)

                output_image_path = os.path.join(method_output_directory, image_file)
                cv2.imwrite(output_image_path, processed_image)
        print(method.__name__, " Completed\n")

    print("Processing complete!")


root = tk.Tk()

button1 = tk.Button(root, text="Select Input Directory", command=select_input_directory)
button1.pack()

input_entry = tk.Entry(root)
input_entry.pack()

button2 = tk.Button(root, text="Select Output Directory", command=select_output_directory)
button2.pack()

output_entry = tk.Entry(root)
output_entry.pack()

button3 = tk.Button(root, text="Process Images", command=process_images)
button3.pack()

root.mainloop()
