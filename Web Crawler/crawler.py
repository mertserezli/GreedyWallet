import  selenium

from selenium import webdriver


baseLink="https://www.carrefoursa.com/tr/tr/"
baseQuery="?q=%3AbestSeller%3AinStockFlag%3Atrue&show=All"

url="https://www.carrefoursa.com/tr/tr/et-sarkuteri-balik/c/1044?q=%3AbestSeller%3AinStockFlag%3Atrue&show=All"
browser = webdriver.Chrome(r'C:\Users\Burak\PycharmProjects\untitled\crawler\chromedriver.exe')


with open('carrefourLink.txt',"r") as file:
    for line in file:

        url=baseLink+line+baseQuery

        urunAnaKategori=line[:line.index("/")]
        browser.get(url)

        urunlerDiv=browser.find_element_by_xpath("/html/body/main/div[4]/div[1]/div[2]/div[2]/div[3]/ul")

        urunler=urunlerDiv.find_elements_by_tag_name("li")

        urunPopularityRank=1
        for urun in urunler:
            urunIndirimYuzde=0
            urunAltKategori= urun.find_element_by_id("productMainCategoryPost").get_attribute("value")
            urunLinki = urun.find_element_by_tag_name("a")
            urunAdi=urunLinki.find_element_by_class_name("item-name").get_attribute("innerHTML")
            urunFiyati=urunLinki.find_element_by_class_name("item-price").get_attribute("innerHTML").replace("TL","")
            if("discount-badge" in urunLinki.get_attribute("innerHTML")):
                urunIndirimYuzde=urunLinki.find_element_by_class_name("discount-badge").get_attribute("innerHTML").replace(" ","")
            print("Ad:",urunAdi)
            print("Fiyat",urunFiyati)
            print("Indirim",urunIndirimYuzde)
            print("Kategori",urunAltKategori)
            urunPopularityRank+=1
