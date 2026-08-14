import os
import shutil
import sys

sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from parser import parse_legal_document
from db_handler import add_chunks_to_db
from config.config import DATA_TO_ADD_PATH, DATA_FINAL_PATH

def process_new_documents():
    all_chunks = []
    processed_files_path = []

    if not os.path.isdir(DATA_TO_ADD_PATH):
        print(f"Thư mục '{DATA_TO_ADD_PATH}' không tồn tại. Kết thúc.")
        return

    for root, _, files in os.walk(DATA_TO_ADD_PATH):
        for file in files:
            if file.endswith('.docx'):
                file_path = os.path.join(root, file)
                all_chunks.extend(parse_legal_document(file_path))
                processed_files_path.append(file_path)

    if not all_chunks:
        print("Không tạo được chunk nào.")
        return
    print(f"\nTổng cộng đã tạo được {len(all_chunks)} chunks.")

    add_chunks_to_db(all_chunks)

    print("\nĐang di chuyển các file đã xử lý vào thư mục lưu trữ...")
    if not os.path.exists(DATA_FINAL_PATH):
        os.makedirs(DATA_FINAL_PATH)

    for source_path in processed_files_path:
        relative_path = os.path.relpath(source_path, DATA_TO_ADD_PATH)
        destination_path = os.path.join(DATA_FINAL_PATH, relative_path)
        os.makedirs(os.path.dirname(destination_path), exist_ok=True)

        shutil.copy2(source_path, destination_path)
        print(f"  - Đã sao chép vào: {destination_path}")

if __name__ == "__main__":
    process_new_documents()
