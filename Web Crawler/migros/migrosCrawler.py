import  selenium
import  json
import time
from selenium import webdriver


baseLink="https://www.migros.com.tr/"
baseQuery="?sirala=cok-satanlar"

url="https://www.carrefoursa.com/tr/tr/et-sarkuteri-balik/c/1044?q=%3AbestSeller%3AinStockFlag%3Atrue&show=All"
browser = webdriver.Chrome(r'C:\Users\Burak\PycharmProjects\untitled\crawler\chromedriver.exe')

stopWords=["2 Al 1 Öde","\n2Al1Öde","Yeni Ürün","'\n3Al2Öde","3 Al 2 Öde","Hediyeli","\n4Al3Öde","4 Al 3 Öde","\n2.si%50","2.si %50","5 Al 4 Öde","2.si %32"]
urunListesi=[]
with open('migrosLink.txt',"r") as file:
    for line in file:

        kategoriArray = line.split("-")
        urunAnaKategori=""

        for elem in kategoriArray:
            if elem=="c":
                break
            urunAnaKategori+=" "+elem
        url=baseLink+line+baseQuery

        browser.get(url)


        urunPopularityRank = 1

        while 1 > 0:
            urunler = browser.find_element_by_class_name("sub-category-product-list").find_elements_by_class_name("list")

            for urun in urunler:
                product = {}
                urunIndirimYuzde = 0

                urunLinki = urun.find_element_by_tag_name("a")

                urunAdi = urunLinki.get_attribute("data-monitor-name")
                urunAltKategori = urunLinki.get_attribute("data-monitor-category")

                urunFiyati = float(
                    urunLinki.get_attribute("data-monitor-price").replace(" ", "").replace("TL", "").replace(",", ".").replace("\n2Al1Öde",""))

                if "campaign-tag" in urun.get_attribute("innerHTML"):
                    isStopWord=False
                    for stopWord in stopWords:
                        if stopWord in urun.get_attribute("innerHTML"):
                            isStopWord=True
                            break
                    if isStopWord:
                        break
                    indirim = float(urun.find_element_by_class_name("campaign-tag").find_element_by_tag_name("span").get_attribute(
                            "innerHTML").replace(" ", "").replace("TL", "").replace(",", ".").replace("\n2Al1Öde",""))
                    urunIndirimYuzde = int(((indirim - urunFiyati) * 100) / indirim)

                product["Isim"] = urunAdi
                product["Fiyat"] = urunFiyati
                product["AnaKategori"] = urunAnaKategori
                product["AltKategori"] = urunAltKategori
                product["Indirim"] = urunIndirimYuzde
                product["PopularityRank"] = urunPopularityRank

                urunPopularityRank += 1

                urunListesi.append(product)
                print(product)
            if "pag-next" in browser.find_element_by_id("page-area").get_attribute("innerHTML"):
                nextPageButton = browser.find_element_by_class_name("pag-next").click()
            else:
                break
            time.sleep(2)
with open("migrosUrunler.json", mode='w', encoding="utf-8") as f:
    f.write(json.dumps(urunListesi, indent=2, ensure_ascii=False))
