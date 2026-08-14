import os
import sys

sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from db_handler import delete_docs_from_db
from config.config import DATA_TO_DELETE_PATH, DATA_FINAL_PATH

def process_old_documents():
    if not os.path.isdir(DATA_TO_DELETE_PATH):
        print(f"Thư mục '{DATA_TO_DELETE_PATH}' không tồn tại. Kết thúc.")
        return

    files_to_delete = []
    paths_to_delete_in_source = []

    for root, _, files in os.walk(DATA_TO_DELETE_PATH):
        for file in files:
            if file.endswith('.docx'):
                files_to_delete.append(file)
                paths_to_delete_in_source.append(os.path.join(root, file))

    if not files_to_delete:
        print("Không tìm thấy tài liệu .docx nào trong thư mục 'data_to_delete'.")
        return
    
    delete_docs_from_db(files_to_delete)

    print("\nĐang xóa các file vật lý đã được xử lý...")
    for source_path in paths_to_delete_in_source:
        relative_path = os.path.relpath(source_path, DATA_TO_DELETE_PATH)
        path_in_final_folder = os.path.join(DATA_FINAL_PATH, relative_path)
        if os.path.exists(path_in_final_folder):
            os.remove(path_in_final_folder)
            print(f"  - Đã xóa file khỏi kho lưu trữ: {path_in_final_folder}")
        else:
            print(f"  - Lưu ý: Không tìm thấy file tương ứng tại: {path_in_final_folder}")


if __name__ == "__main__":
    process_old_documents()
